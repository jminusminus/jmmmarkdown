//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.jminusminus.markdown;

public class Block {

    protected String block;

    Block(String block) {
        this.block = block.substring(4);
    }

    public String toString() {
        return this.block + Markdown.LF;
    }
}
