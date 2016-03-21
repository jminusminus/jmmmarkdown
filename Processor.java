//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

import github.com.ricallinson.http.util.Map;
import github.com.ricallinson.http.util.LinkedListMap;
import github.com.ricallinson.http.io.BufferedInputStreamReader;

public class Processor {

    // Line feed.
    public static final byte LF = "\n".getBytes()[0];

    // Holds the found token elements.
    protected Map elements = new LinkedListMap();

    // Empty line
    public String parse(String str) {
        return "";
    }

    protected int tokenizeBlocks(BufferedInputStreamReader buf) {
        String line;
        int index = 0;
        while(buf.hasMore()) {
            line = new String(buf.readTo(this.LF));
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
