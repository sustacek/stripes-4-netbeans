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
public class Label extends CodeSnippet {

    private static final String BUNDLE_KEY = "Label";
    
    static final String FOR = "for";    
    
    private static final String END_TAG = "</" + STRIPES_PREFIX + ":label>";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":label " +
                "for=\"" + getProperty(FOR) + "\" >" + 
                
                END_TAG;
    }

    @Override
    protected int getCaretShift(int caretIndent) {
        return (-1) * END_TAG.length(); 
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new LabelCustomizer(snippet);
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
