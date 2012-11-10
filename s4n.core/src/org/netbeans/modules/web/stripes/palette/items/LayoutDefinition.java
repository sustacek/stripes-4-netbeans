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
public class LayoutDefinition extends CodeSnippet{

    private static final String BUNDLE_KEY = "LayoutDef";
    
    private static final String END_TAG = 
            "</" + STRIPES_PREFIX + ":layout-definition>";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":layout-definition>\n" + 
                indent("\n", caretPositionOnLine) +
            indent(END_TAG, caretPositionOnLine);
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return null;
    }

    @Override
    protected int getCaretShift(int caretIndent) {
        return (-1) * (END_TAG.length() + 1 + caretIndent); // +1 for new line
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
