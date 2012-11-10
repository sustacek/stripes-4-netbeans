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

public class LayoutComponent extends CodeSnippet {

    private static final String BUNDLE_KEY = "LayoutComp";
    static final String NAME = "name";
    
    private static final String END_TAG = 
            "</" + STRIPES_PREFIX + ":layout-component>";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        String tag = "<" + STRIPES_PREFIX + ":layout-component " +
                "name=\"" + getProperty(NAME) + "\" >" +
                
                END_TAG;
        return tag;
    }
    
    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new LayoutComponentCustomizer(snippet);
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
