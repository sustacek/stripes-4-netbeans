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
public class Text extends Input {

    private static final String BUNDLE_KEY = "Text";
    
    static final String VALUE = "value";
        static final String DISABLED = "disabled";
    static final String READONLY = "readonly";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":text " +
                "name=\"" + getProperty(NAME) + "\" " + 
                (!StringUtils.nullOrEmpty(getProperty(DISABLED)) ? 
                    "disabled=\"disabled\" " : "") +
                (!StringUtils.nullOrEmpty(getProperty(READONLY)) ? 
                    "readonly=\"readonly\" " : "") +    
                
                "/>";
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new TextCustomizer(snippet);
    }
    
    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }
}
