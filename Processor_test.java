//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

import github.com.jminusminus.simplebdd.Test;

public class Processor_test extends Test {
    public static void main(String[] args) {
        Processor_test test = new Processor_test();
        test.run();
    }

    public void test_new_Processor() {
        this.should("return an instance of Processor");
        Processor p = new Processor();
        this.assertEqual("github.com.ricallinson.jmmmarkdown.Processor", p.getClass().getName());
    }

    public void test_tokenize() {
        this.should("return the count of block tokens found");
        Processor p = new Processor();
        int count = p.tokenize(this.getBasic());
        this.assertEqual(35, count);
    }

    public void test_parse() {
        this.should("return HTML");
        Processor p = new Processor();
        String html = p.parse(this.getBasic());
        this.assertEqual("<H5>Heading 5</H5>", html.substring(72, 90));
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
