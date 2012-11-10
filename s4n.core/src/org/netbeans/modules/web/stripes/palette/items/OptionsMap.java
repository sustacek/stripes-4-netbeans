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
public class OptionsMap extends CodeSnippet {

    private static final String BUNDLE_KEY = "OptsMap";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":options-map " +
                "map=\"\" " +
                
                "/>";
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return null;
    }

    @Override
    protected int getCaretShift(int caretIndent) {
        return (-1) * "\" />".length();
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
