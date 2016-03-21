//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

public class List {

    protected String list;

    List(String list) {
        this.list = list;
    }

    public String toString() {
        return "<li>" + this.list.trim() + "</li>";
    }
}
