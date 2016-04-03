//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

import github.com.jminusminus.simplebdd.Test;

public class Markdown_test extends Test {
    public static void main(String[] args) {
        Markdown_test test = new Markdown_test();
        test.run();
    }

    public void test_new_Markdown() {
        this.should("return an instance of Markdown");
        Markdown p = new Markdown();
        this.assertEqual("github.com.ricallinson.jmmmarkdown.Markdown", p.getClass().getName());
    }

    public void test_parse() {
        this.should("return HTML from a static method");
        String html = Markdown.parse(this.getBasic()).toString();
        this.assertEqual(true, html.contains("<H4>Heading 4</H4>"));
    }

    public void test_tokenize() {
        this.should("return the count of block tokens found");
        Markdown p = new Markdown();
        int count = p.tokenize(this.getBasic());
        this.assertEqual(35, count);
    }

    public void test_parse_paragraph() {
        this.should("contain a P tag");
        Markdown p = new Markdown();
        String html = p.parseStr("some text").toString();
        this.assertEqual("<p>some text</p>\n", html);
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
        this.should("contain a PRE tag");
        Markdown p = new Markdown();
        String html = p.parseStr(this.getBasic()).toString();
        this.assertEqual(true, html.contains("<pre>    block quote 1"));
        this.assertEqual(true, html.contains("    block quote 4\n</pre>"));
    }

    public void test_parse_code() {
        this.should("contain a PRE tag with code");
        Markdown p = new Markdown();
        String html = p.parseStr(this.getBasic()).toString();
        this.assertEqual(true, html.contains("<pre><code>code block 1"));
        this.assertEqual(true, html.contains("code block 4\n</code></pre>"));
    }

    public void test_parse_inline_link() {
        this.should("return an A tag");
        Markdown p = new Markdown();
        String html = p.parseStr("[http://foo.com/](text)").toString();
        this.assertEqual("<p><a href=\"http://foo.com/\">text</a></p>\n", html);
    }

    public void test_parse_inline_image() {
        this.should("return an IMG tag");
        Markdown p = new Markdown();
        String html = p.parseStr("![http://foo.com/](text)").toString();
        this.assertEqual("<p><img src=\"http://foo.com/\" alt=\"text\"></p>\n", html);
    }

    public void test_parse_inline_bold() {
        this.should("return an B tag");
        Markdown p = new Markdown();
        String html = p.parseStr("__some text__").toString();
        this.assertEqual("<p><b>some text</b></p>\n", html);
    }

    public void test_parse_inline_em() {
        this.should("return an EM tag");
        Markdown p = new Markdown();
        String html = p.parseStr("_some text_").toString();
        this.assertEqual("<p><em>some text</em></p>\n", html);
    }

    public void test_parse_inline_pre() {
        this.should("return an PRE tag inline");
        Markdown p = new Markdown();
        String html = p.parseStr("`some text`").toString();
        this.assertEqual("<p><pre>some text</pre></p>\n", html);
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
