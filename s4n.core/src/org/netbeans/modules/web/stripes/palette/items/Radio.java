/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.palette.items;

import org.netbeans.modules.web.stripes.palette.CodeSnippet;
import org.netbeans.modules.web.stripes.palette.CodeSnippetCustomizer;
import javax.swing.text.JTextComponent;
import org.netbeans.modules.web.stripes.util.StringUtils;

/**
 *
 * @author Josef Sustacek
 */
public class Radio extends Input {

    private static final String BUNDLE_KEY = "Radio";
    
    static final String VALUE = "value";
    static final String CHECKED = "checked";
    static final String DISABLED = "disabled";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":radio " +
                "name=\"" + getProperty(NAME) + "\" " + 
                "value=\"" + getProperty(VALUE) + "\" " +
                (!StringUtils.nullOrEmpty(getProperty(CHECKED)) ? 
                    "checked=\"checked\" " : "") +
                (!StringUtils.nullOrEmpty(getProperty(DISABLED)) ? 
                    "disabled=\"disabled\" " : "") +
                
                "/>";
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new RadioCustomizer(snippet);
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
