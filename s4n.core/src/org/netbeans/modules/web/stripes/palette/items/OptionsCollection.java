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
public class OptionsCollection extends CodeSnippet{

    private static final String BUNDLE_KEY = "OptsColl";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":options-collection " +
                "collection=\"\" " +
                "value=\"\" " +
                "label=\"\" " +
                
                "/>";
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return null;
    }

    @Override
    protected int getCaretShift(int caretIndent) {
        return (-1) * ("\" value=\"\" label=\"\" />").length();
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
