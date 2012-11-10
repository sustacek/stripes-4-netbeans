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
public abstract class Input extends CodeSnippet{

    static final String NAME = "name";

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new InputCustomizer(snippet);
    }
    
    @Override
    protected abstract String createBody(int caretPositionOnLine);

    @Override
    protected abstract String getBundleKey();
}
