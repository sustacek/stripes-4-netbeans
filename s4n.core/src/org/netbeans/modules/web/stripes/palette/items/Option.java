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
public class Option extends CodeSnippet{

    private static final String BUNDLE_KEY = "Option";
    
    static final String VALUE = "value";
    
    private static final String END_TAG = "</" + STRIPES_PREFIX + ":option>";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":option " +
                "value=\"" + getProperty(VALUE) + "\" >" + 
                
                END_TAG;
    }

    @Override
    protected int getCaretShift(int caretIndent) {
        return (-1) * END_TAG.length(); 
    }
    
    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new OptionCustomizer(snippet);
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }
}
