
# Markdown
#### Package: github.com.jminusminus.markdown
[![Build Status](https://travis-ci.org/jminusminus/markdown.svg?branch=master)](https://travis-ci.org/jminusminus/markdown)
## Stability: 0 - Unstable
This package provides a simplified [Markdown](https://en.wikipedia.org/wiki/Markdown) text-to-HTML conversion tool.

## Install
```
jmm get github.com/jminusminus/markdown
```
## Class: github.com.jminusminus.markdown.Markdown
```
import github.com.jminusminus.markdown.Markdown;
```
## Syntax

### Paragraphs

A paragraph is simply one or more consecutive lines of text, separated by one or more blank lines.

### Headers

Headers use 1-6 hash characters at the start of the line, corresponding to header levels 1-6. For example:

    # This is an H1
    ## This is an H2
    ###### This is an H6

### Blockquotes

To produce a code block in Markdown, simply indent every line of the block by at least 4 spaces. For example, given this input:

    This is a normal paragraph:

         This is a code block.

### Lists

Unordered lists use asterisks, pluses, and hyphens interchangeably as list markers:

    * Red
    * Green
    * Blue

is equivalent to:

    + Red
    + Green
    + Blue

and:

    - Red
    - Green
    - Blue

### Code Blocks

Pre-formatted code blocks are used for writing about programming or markup source code.

    ```
    function foo() {
         return "bar";
    }
    ```

### Links

To create an inline link, use a set of regular parentheses immediately after the link texts closing square bracket. Inside the parentheses, put the URL where you want the link to point. For example:

    This is [an example](http://example.com/) inline link.

### Emphasis

Markdown treats underscores (_) as indicators of emphasis. Text wrapped with one (_) will be wrapped with an HTML &lt;em&gt; tag; double (_)'s will be wrapped with an HTML &lt;strong&gt; tag. E.g., this input:

    _single underscores_

    __double underscores__

### Code

To indicate a span of code, wrap it with back tick quotes (`). Unlike a pre-formatted code block, a code span indicates code within a normal paragraph. For example:

    Use the `printf()` function.

### images

Markdown uses an image syntax that is intended to resemble the syntax for links, allowing for two styles: inline and reference.

Inline image syntax looks like this:

    ![Alt text](/path/to/img.jpg)

Images can also be placed as link text.


## Attributes
## static final String LF = "\n"
Line feed.

## Methods
## static String parse(String str)
Returns HTML from the given Markdown string.

## Markdown parseStr(String str)
Returns a Markdown instance with the given string parsed.

## String toString()
Returns HTML from the given Markdown.

