//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

public class Paragraph {

    protected String paragraph;

    Paragraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String toString() {
        return "<p>" + this.parseString(this.paragraph).trim() + "</p>\n";
    }

    protected String parseString(String str) {
        String[] tokens = str.split(" ");
        String complete = "";
        int index = 0;
        while (index < tokens.length) {
            complete += this.parseToken(tokens, index) + " ";
            index++;
        }
        return complete;
    }

    protected String parseToken(String[] tokens, int index) {
        if (tokens[index].isEmpty()) {
            return "";
        }
        switch (tokens[index].charAt(0)) {
            case '!': // Check for image
                // Look at future tokens to find the last round bracket "![]()"
            case '[': // Check for link
                // Look at future tokens to find the last round bracket "[]()"
                return tokens[index];
            case '_': // Check for emphasis
                // Count the number of underscores and then look for the matching count in future tokens.
                return tokens[index];
            case '`': // Check for code
                // Count the number of back ticks and then look for the matching count in future tokens.
                return tokens[index];
            default: // Text
                return tokens[index];
        }
    }
}
