//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

import github.com.ricallinson.http.util.Map;
import github.com.ricallinson.http.util.LinkedListMap;

public class Processor {

    // Line feed.
    public static final String LF = "\n";

    // Holds the found token elements.
    protected Map elements = new LinkedListMap();

    // The rendered HTML.
    protected String html = "";

    // Empty line
    public String parse(String str) {
        this.tokenize(str);
        this.elements.forEach((element) -> {
            this.html += element.value().toString();
        });
        return this.html;
    }

    protected int tokenize(String str) {
        String[] lines = str.split(this.LF);
        int index = 0;
        for (String line : lines) {
            if (line.trim().length() > 0) {
                this.parseLine(index, line);
                index++;
            }
        }
        return index;
    }

    // Check for paragraph
    // Check for header
    // Check for block quote
    // Check for list
    // Check for code block
    // Check for link
    // Check for emphasis
    // Check for code
    // Check for image
    protected void parseLine(int index, String line) {
        switch (line.charAt(0)) {
            case '#':
                this.elements.put(index, new Title(line));
                break;
            case '*':
            case '+':
            case '-':
                this.elements.put(index, new List(line));
                break;
            default:
                this.elements.put(index, new Paragraph(line));
                break;
        }
    }
}
