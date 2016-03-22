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
        return "<p>" + this.paragraph.trim() + "</p>\n";
    }
}
