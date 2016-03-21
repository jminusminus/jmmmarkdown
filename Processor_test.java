//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

import github.com.ricallinson.http.io.BufferedInputStreamReader;
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
        BufferedInputStreamReader reader = this.getBasic();
        int count = p.tokenizeBlocks(reader);
        this.assertEqual(35, count);
    }

    protected BufferedInputStreamReader getBasic() {
        try {
            java.io.File initialFile = new java.io.File("fixtures/basic.md");
            java.io.InputStream targetStream = new java.io.FileInputStream(initialFile);
            return new BufferedInputStreamReader(targetStream);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
