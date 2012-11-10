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
public class Button extends Input{

    private static final String BUNDLE_KEY = "Button";
    private static final String END_TAG = "</" + STRIPES_PREFIX + ":button>";
    
    static final String DISABLED = "disabled";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":button " +
                "name=\"" + getProperty(NAME) + "\" " + 
                (!StringUtils.nullOrEmpty(getProperty(DISABLED)) ? 
                    "disabled=\"disabled\" " : "") +
                ">" +
                    
                END_TAG;
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new ButtonCustomizer(snippet);
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
