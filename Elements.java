//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

public class Elements {

    protected String[] tokens;
    protected int length;
    protected int index = 0;

    Elements(String elements) {
        this.tokens = elements.split(" ");
        this.length = this.tokens.length;
    }

    public String toString() {
        String elements = "";
        String element;
        while (this.index < this.tokens.length) {
            element = this.parseToken();
            if (element.isEmpty() == false) {
                elements += element + " ";
            }
        }
        return elements.trim();
    }

    protected String parseToken() {
        if (this.tokens[this.index].isEmpty()) {
            this.index++;
            return "";
        }
        switch (this.tokens[this.index].charAt(0)) {
            case '!': // Check for image
                return this.parseImage();
            case '[': // Check for link
                return this.parseLink();
            case '_': // Check for emphasis
                return this.parseEmphasis();
            case '`': // Check for code
                return this.parseCode();
            default: // Text
                this.index++;
                return this.tokens[this.index - 1];
        }
    }

    // Look at future tokens to find the last round bracket "[]()"
    // [http://foo.com/](some text)
    // <a href="http://foo.com/">text</a>
    protected String parseLink() {
        this.index++;
        return this.tokens[this.index - 1];
    }

    // Look at future tokens to find the last round bracket "![]()"
    // ![http://foo.com/](some text)
    // <img src="http://foo.com/" title="text">
    protected String parseImage() {
        this.index++;
        return this.tokens[this.index - 1];
    }

    // Count the number of underscores and then look for the matching count in future tokens.
    // _some text_
    // <em>some text</em>
    // __some text__
    // <b>some text</b>
    protected String parseEmphasis() {
        this.index++;
        return this.tokens[this.index - 1];
    }

    // Count the number of back ticks and then look for the matching count in future tokens.
    // `some text`
    // <pre>some text</pre>
    protected String parseCode() {
        String token = "";
        String element = "";
        int end = 0;
        while (this.index < this.length) {
            element += this.tokens[this.index] + " ";
            end = element.length();
            if (element.charAt(end - 1) == '`') {
                break;
            }
            this.index++;
        }
        return "<pre>" + element.substring(1, end - 2) + "</pre>";
    }
}
