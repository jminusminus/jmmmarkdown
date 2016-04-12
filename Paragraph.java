//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.jminusminus.markdown;

public class Paragraph {

    protected String paragraph;

    Paragraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public String toString() {
        return (new Elements(this.paragraph).toString());
    }
}
