//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

public class Code {

    protected String code;

    Code(String code) {
        this.code = code;
    }

    public String toString() {
        return this.code + "\n";
    }
}
