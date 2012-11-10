/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.palette.items;

import org.netbeans.modules.web.stripes.util.StringUtils;

/**
 *
 * @author Josef Sustacek
 */
public class Submit extends Button{

    private static final String BUNDLE_KEY = "Submit";
    private static final String END_TAG = "</" + STRIPES_PREFIX + ":submit>";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":submit " +
                "name=\"" + getProperty(NAME) + "\" " +
                (!StringUtils.nullOrEmpty(getProperty(DISABLED)) ? 
                    "disabled=\"disabled\" " : "") +
                
                ">" + 
                END_TAG;
    }

    @Override
    protected int getCaretShift(int caretIndent) {
        return (-1) * END_TAG.length();
    }
    
    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }
}
