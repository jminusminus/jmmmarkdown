//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

import github.com.ricallinson.http.util.Map;
import github.com.ricallinson.http.util.LinkedListMap;

public class Markdown {

    // Line feed.
    public static final String LF = "\n";

    // Holds the found token elements.
    protected Map elements = new LinkedListMap();

    // The rendered HTML.
    protected String html = "";

    // Marks if a loop is in a code block.
    protected boolean isCode = false;

    // Marks if a loop is in a list.
    protected boolean isList = false;

    // Marks if a loop is in a block.
    protected boolean isBlock = false;

    // Returns HTML from the given Markdown.
    public static String parse(String str) {
        Markdown md = new Markdown();
        return md.parseStr(str).toString();
    }

    // Returns HTML from the given Markdown.
    public Markdown parseStr(String str) {
        this.tokenize(str);
        return this;
    }

    // Returns HTML from the given Markdown.
    public String toString() {
        this.elements.forEach((element) -> {
            String type = element.value().getClass().getName();
            this.html += this.isList(type);
            this.html += this.isCode(type);
            this.html += this.isBlock(type);
            this.html += element.value().toString();
        });
        return this.html;
    }

    protected String isList(String type) {
        if (!this.isList && type.contains("List")) {
            this.isList = true;
            return "<ul>\n";
        }
        if (this.isList && !type.contains("List")) {
            this.isList = false;
            return "</ul>\n";
        }
        return "";
    }

    protected String isCode(String type) {
        if (!this.isCode && type.contains("Code")) {
            this.isCode = true;
            return "<pre>\n";
        }
        if (this.isCode && !type.contains("Code")) {
            this.isCode = false;
            return "</pre>\n";
        }
        return "";
    }

    protected String isBlock(String type) {
        if (!this.isBlock && type.contains("Block")) {
            this.isBlock = true;
            return "<pre>\n";
        }
        if (this.isBlock && !type.contains("Block")) {
            this.isBlock = false;
            return "</pre>\n";
        }
        return "";
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

    // Check for link
    // Check for emphasis
    // Check for image
    protected void parseLine(int index, String line) {
        switch (line.charAt(0)) {
            case '#': // Check for header
                this.elements.put(index, new Title(line));
                return;
            case '*': // Check for list
            case '+': // Check for list
            case '-': // Check for list
                this.elements.put(index, new List(line));
                return;
            default: // The line must be part of a block.
                this.parseBlock(index, line);
        }
    }

    protected void parseBlock(int index, String line) {
        switch (line.charAt(0)) {
            case ' ': // Check for block quote
                if (!this.isCode && "    ".equals(line.substring(0, 4))) {
                    this.elements.put(index, new Block(line));
                    return;
                }
            case '`': // Check for code
                if ("```".equals(line.substring(0, 3))) {
                    this.isCode = !this.isCode;
                    return;
                }
            default: // Check for paragraph
                if (this.isCode) {
                    this.elements.put(index, new Code(line));
                    return;
                }
                this.elements.put(index, new Paragraph(line));
        }
    }
}
