//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.jminusminus.markdown;

import github.com.jminusminus.simplebdd.Test;

public class Markdown_test extends Test {
    public static void main(String[] args) {
        Markdown_test test = new Markdown_test();
        test.run();
    }

    public void test_new_Markdown() {
        this.should("return an instance of Markdown");
        Markdown p = new Markdown();
        this.assertEqual("github.com.jminusminus.markdown.Markdown", p.getClass().getName());
    }

    public void test_parse_basic() {
        this.should("return HTML from a static method");
        String html = Markdown.parse(this.getBasic()).toString();
        this.assertEqual(true, html.contains("<H4>Heading 4</H4>"));
    }

    public void test_tokenize() {
        this.should("return the count of block tokens found");
        Markdown p = new Markdown();
        int count = p.tokenize(this.getBasic());
        this.assertEqual(48, count);
    }

    public void test_parse_paragraph() {
        this.should("contain a P tag");
        Markdown p = new Markdown();
        String html = p.parseStr("some text").toString();
        this.assertEqual("<p>some text</p>\n", html);
    }

    public void test_parse_paragraph_lines() {
        this.should("contains a P tag with many lines");
        Markdown p = new Markdown();
        String html = p.parseStr("some text.\nmore text.\nand more.").toString();
        this.assertEqual("<p>some text. more text. and more.</p>\n", html);
    }

    public void test_parse_paragraph_starting_with_space() {
        this.should("contain a P tag that started with a space");
        Markdown p = new Markdown();
        String html = p.parseStr("   some text").toString();
        this.assertEqual("<p>some text</p>\n", html);
    }

    public void test_parse_heading() {
        this.should("contain a H5 tag");
        Markdown p = new Markdown();
        String html = p.parseStr(this.getBasic()).toString();
        this.assertEqual(true, html.contains("<H5>Heading 5</H5>"));
    }

    public void test_parse_block() {
        this.should("contains a PRE tag from code block");
        Markdown p = new Markdown();
        String html = p.parseStr(this.getBasic()).toString();
        this.assertEqual(true, html.contains("<pre><code>block quote 1"));
        this.assertEqual(true, html.contains("block quote 4\n</code></pre>"));
    }

    public void test_parse_code() {
        this.should("contain a PRE tag from code quote");
        Markdown p = new Markdown();
        String html = p.parseStr(this.getBasic()).toString();
        this.assertEqual(true, html.contains("<pre><code>code block 1"));
        this.assertEqual(true, html.contains("code block 4\n</code></pre>"));
    }

    public void test_parse_inline_link() {
        this.should("return an A tag");
        Markdown p = new Markdown();
        String html = p.parseStr("[text](http://foo.com/)").toString();
        this.assertEqual("<p><a href=\"http://foo.com/\">text</a></p>\n", html);
    }

    public void test_parse_inline_link_with_text() {
        this.should("return an A tag with text around it");
        Markdown p = new Markdown();
        String html = p.parseStr("a link of [text](http://foo.com/) to nowhere").toString();
        this.assertEqual("<p>a link of <a href=\"http://foo.com/\">text</a> to nowhere</p>\n", html);
    }

    public void test_parse_inline_link_with_image() {
        this.should("return an A tag with an image in it");
        Markdown p = new Markdown();
        String html = p.parseStr("[![image](http://image.com/)](http://foo.com/)").toString();
        this.assertEqual("<p><a href=\"http://foo.com/\"><img src=\"http://image.com/\" alt=\"image\"></a></p>\n", html);
    }

    public void test_parse_inline_image() {
        this.should("return an IMG tag");
        Markdown p = new Markdown();
        String html = p.parseStr("![text](http://foo.com/)").toString();
        this.assertEqual("<p><img src=\"http://foo.com/\" alt=\"text\"></p>\n", html);
    }

    public void test_parse_inline_image_with_text() {
        this.should("return an IMG tag with text around it");
        Markdown p = new Markdown();
        String html = p.parseStr("the image ![text](http://foo.com/) of nothing").toString();
        this.assertEqual("<p>the image <img src=\"http://foo.com/\" alt=\"text\"> of nothing</p>\n", html);
    }

    public void test_parse_inline_code() {
        this.should("return a CODE tag");
        Markdown p = new Markdown();
        String html = p.parseStr("words `code here` more words").toString();
        this.assertEqual("<p>words <code>code here</code> more words</p>\n", html);
    }

    public void test_parse_inline_code_all() {
        this.should("return a CODE tag wrapping all the text");
        Markdown p = new Markdown();
        String html = p.parseStr("`code here`\r\n").toString();
        this.assertEqual("<p><code>code here</code></p>\n", html);
    }

    public void test_parse_inline_code_start() {
        this.should("return a CODE tag at the start");
        Markdown p = new Markdown();
        String html = p.parseStr("\n`code here` more words").toString();
        this.assertEqual("<p><code>code here</code> more words</p>\n", html);
    }

    public void test_parse_inline_code_end() {
        this.should("return a CODE tag at the end");
        Markdown p = new Markdown();
        String html = p.parseStr("words `code here`\n").toString();
        this.assertEqual("<p>words <code>code here</code></p>\n", html);
    }

    public void test_parse_inline_bold() {
        this.should("return an B tag");
        Markdown p = new Markdown();
        String html = p.parseStr("__some text__").toString();
        this.assertEqual("<p><strong>some text</strong></p>\n", html);
    }

    public void test_parse_inline_em() {
        this.should("return an EM tag");
        Markdown p = new Markdown();
        String html = p.parseStr("_some text_").toString();
        this.assertEqual("<p><em>some text</em></p>\n", html);
    }

    protected String getBasic() {
        try {
            byte[] encoded = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("fixtures/basic.md"));
            return new String(encoded, "UTF8");
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
