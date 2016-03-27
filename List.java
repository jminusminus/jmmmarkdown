//
// Copyright 2016, Yahoo Inc.
// Copyrights licensed under the New BSD License.
// See the accompanying LICENSE file for terms.
//

package github.com.ricallinson.jmmmarkdown;

public class List {

    protected String list;

    List(String list) {
    	// Remove the list token.
        this.list = list.substring(1);
    }

    public String toString() {
        return "<li>" + new Elements(this.list).toString() + "</li>" + Markdown.LF;
    }
}
