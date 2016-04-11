//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.jminusminus.markdown;

import github.com.jminusminus.simplebdd.Test;

public class Title_test extends Test {
    public static void main(String[] args) {
        Title_test test = new Title_test();
        test.run();
    }

    public void test_new_Title() {
        this.should("return an instance of Title");
        Title t = new Title("");
        this.assertEqual("github.com.jminusminus.markdown.Title", t.getClass().getName());
    }

    public void test_h1() {
        this.should("return a level of 1");
        Title t = new Title("# 1");
        this.assertEqual(1, t.level);
        this.assertEqual("1", t.text);
    }

    public void test_h2() {
        this.should("return a level of 2");
        Title t = new Title("##2");
        this.assertEqual(2, t.level);
        this.assertEqual("2", t.text);
    }

    public void test_h5() {
        this.should("return a level of 5");
        Title t = new Title("##### 5");
        this.assertEqual(5, t.level);
        this.assertEqual("5", t.text);
    }

    public void test_toString() {
        this.should("return HTML");
        Title t = new Title("##### Five");
        this.assertEqual("<H5>Five</H5>\n", t.toString());
    }
}
