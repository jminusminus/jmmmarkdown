//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

// [![Build Status](https://travis-ci.org/jminusminus/markdown.svg?branch=master)](https://travis-ci.org/jminusminus/markdown)
// ## Stability: 0 - Unstable
// This package provides a simplified [Markdown](https://en.wikipedia.org/wiki/Markdown) text-to-HTML conversion tool.
package github.com.jminusminus.markdown;

import github.com.jminusminus.http.util.Map;
import github.com.jminusminus.http.util.LinkedListMap;

// ## Syntax
//
// ### Paragraphs
//
// A paragraph is simply one or more consecutive lines of text, separated by one or more blank lines.
// 
// ### Headers
//
// Headers use 1-6 hash characters at the start of the line, corresponding to header levels 1-6. For example:
//
//     # This is an H1
//     ## This is an H2
//     ###### This is an H6
//
// ### Lists
//
// Unordered lists use asterisks, pluses, and hyphens interchangeably as list markers:
//
//     * Red
//     * Green
//     * Blue
//
// is equivalent to:
//
//     + Red
//     + Green
//     + Blue
//
// and:
//
//     - Red
//     - Green
//     - Blue
//
// ### Code Blocks
//
// To produce a code block in Markdown, simply indent every line of the block by at least 4 spaces. For example, given this input:
//
//     This is a normal paragraph:
//     
//         This is a code block.
//
// For pre-formatted code blocks specifing a langauge use three back tick quotes (```) on a line by them selfs.
//
//     ```
//     function foo() {
//         return "bar";   
//     }
//     ```
//
// Optional you can provide a language hint.
//
//     ```javascript
//     function foo() {
//         return "bar";   
//     }
//     ```
// ### Links
// 
// To create an inline link, use a set of regular parentheses immediately after the link texts closing square bracket. Inside the parentheses, put the URL where you want the link to point. For example:
//
//     This is [an example](http://example.com/) inline link.
//
// ### Emphasis
//
// Markdown treats underscores (_) as indicators of emphasis. Text wrapped with one (_) will be wrapped with an HTML &lt;em&gt; tag; double (_)'s will be wrapped with an HTML &lt;strong&gt; tag. E.g., this input:
//
//     _single underscores_
//     
//     __double underscores__
//
// ### Code
//
// To indicate a span of code, wrap it with back tick quotes (`). Unlike a pre-formatted code block, a code span indicates code within a normal paragraph. For example:
//
//     Use the `printf()` function.
// 
// ### images
//
// Markdown uses an image syntax that is intended to resemble the syntax for links, allowing for two styles: inline and reference.
// 
// Inline image syntax looks like this:
//
//     ![Alt text](/path/to/img.jpg)
//
// Images can also be placed as link text.
//
public class Markdown {

    // Line feed.
    public static final String LF = "\n";

    // Holds the found token elements.
    protected Map elements = new LinkedListMap();

    // The rendered HTML.
    protected String html = "";

    // Marks if a loop is in a paragraph.
    protected boolean isParagraph = false;

    // Marks if a loop is in a code block.
    protected boolean isCode = false;

    // Marks if a loop is in a list.
    protected boolean isList = false;

    // Marks if a loop is in a block.
    protected boolean isBlock = false;

    // Returns HTML from the given Markdown string.
    public static String parse(String str) {
        Markdown md = new Markdown();
        return md.parseStr(str).toString();
    }

    // Returns a Markdown instance with the given string parsed.
    public Markdown parseStr(String str) {
        this.tokenize(str);
        return this;
    }

    // Returns HTML from the given Markdown.
    public String toString() {
        this.elements.forEach((element) -> {
            String type = element.value().getClass().getName();
            this.html += this.isParagraph(type);
            this.html += this.isList(type);
            this.html += this.isCode(type);
            // this.html += this.isBlock(type);
            this.html += element.value().toString();
        });
        // Close out any remaining elements.
        this.html += this.isParagraph("");
        this.html += this.isList("");
        this.html += this.isCode("");
        // this.html += this.isBlock("");
        return this.html;
    }

    protected String isParagraph(String type) {
        if (this.isCode || this.isBlock) {
            return "";
        }
        if (!this.isParagraph && type.contains("Paragraph")) {
            this.isParagraph = true;
            return "<p>";
        }
        if (this.isParagraph && !type.contains("Paragraph")) {
            this.isParagraph = false;
            return "</p>" + Markdown.LF;
        }
        if (this.isParagraph) {
            return "<br/>";
        }
        return "";
    }

    protected String isList(String type) {
        if (!this.isList && type.contains("List")) {
            this.isList = true;
            return "<ul>" + Markdown.LF;
        }
        if (this.isList && !type.contains("List")) {
            this.isList = false;
            return "</ul>" + Markdown.LF;
        }
        return "";
    }

    protected String isCode(String type) {
        if (!this.isCode && type.contains("Code")) {
            this.isCode = true;
            return "<pre><code>";
        }
        if (this.isCode && !type.contains("Code")) {
            this.isCode = false;
            return "</code></pre>" + Markdown.LF;
        }
        return "";
    }

    // protected String isBlock(String type) {
    //     if (!this.isBlock && type.contains("Block")) {
    //         this.isBlock = true;
    //         return "<pre>";
    //     }
    //     if (this.isBlock && !type.contains("Block")) {
    //         this.isBlock = false;
    //         return "</pre>" + Markdown.LF;
    //     }
    //     return "";
    // }

    protected int tokenize(String str) {
        String[] lines = str.split(Markdown.LF);
        int index = 0;
        for (String line : lines) {
            if (line.trim().length() > 0 || this.isCode) {
                this.parseLine(index, line);
                index++;
            }
        }
        return index;
    }

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
                    this.elements.put(index, new Code(line.substring(4)));
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
