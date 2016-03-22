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

    public void test_tokenize() {
        this.should("return the count of block tokens found");
        Markdown p = new Markdown();
        int count = p.tokenize(this.getBasic());
        this.assertEqual(35, count);
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
        this.assertEqual(true, html.contains("<pre>    block quote 4</pre>"));
    }

    public void test_parse_string_check() {
        this.should("contain a PRE tag");
        Markdown p = new Markdown();
        String html = p.parseStr(this.getBasic()).toString();
        this.assertEqual("", html);
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
