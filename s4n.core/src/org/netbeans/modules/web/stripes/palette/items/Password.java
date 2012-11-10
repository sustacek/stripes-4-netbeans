/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.palette.items;

/**
 *
 * @author Josef Sustacek
 */
public class Password extends Input {

    private static final String BUNDLE_KEY = "Password";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":password " +
                "name=\"" + getProperty(NAME) + "\" />";
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }
}
