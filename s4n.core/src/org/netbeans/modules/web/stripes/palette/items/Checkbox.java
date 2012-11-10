/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.palette.items;

import javax.swing.text.JTextComponent;
import org.netbeans.modules.web.stripes.palette.CodeSnippet;
import org.netbeans.modules.web.stripes.palette.CodeSnippetCustomizer;
import org.netbeans.modules.web.stripes.util.StringUtils;

/**
 *
 * @author Josef Sustacek
 */
public class Checkbox extends Input {

    private static final String BUNDLE_KEY = "Checkbox";
    
    protected static final String CHECKED = "checked";
    protected static final String DISABLED = "disabled";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":checkbox " +
                "name=\"" + getProperty(NAME) + "\" " +
                (!StringUtils.nullOrEmpty(getProperty(CHECKED)) ? 
                    "checked=\"checked\" " : "") +
                (!StringUtils.nullOrEmpty(getProperty(DISABLED)) ? 
                    "disabled=\"disabled\" " : "") +
                
                "/>";
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new CheckboxCustomizer(snippet);
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
