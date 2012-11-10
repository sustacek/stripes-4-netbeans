/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.palette.items;

import org.netbeans.modules.web.stripes.palette.CodeSnippet;
import org.netbeans.modules.web.stripes.palette.CodeSnippetCustomizer;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Josef Sustacek
 */
public class OptionsEnumeration extends CodeSnippet{

    static final String ENUM = "enum";
    
    private static final String BUNDLE_KEY = "OptsEnum";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":options-enumeration " +
                "enum=\"" + getProperty(ENUM) + "\" " +
                
                "/>";
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new OptionsEnumerationCustomizer(snippet, target);
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }
}
