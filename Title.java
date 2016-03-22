//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

public class Title {

    protected int level = 0;

    protected String text = "";

    Title(String title) {
        while (title.length() > 0 && title.charAt(0) == '#') {
            this.level++;
            title = title.substring(1);
        }
        this.text = title.trim();
    }

    public String toString() {
        return String.format("<H%d>%s</H%d>" + Markdown.LF, this.level, this.text, this.level);
    }
}
