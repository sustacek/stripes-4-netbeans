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
public class Image extends Input {

    private static final String BUNDLE_KEY = "Image";
    
    static final String SRC = "src";
    static final String ALT = "alt";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":image " +
                "name=\"" + getProperty(NAME) + "\" " +
                "src=\"" + getProperty(SRC) + "\" " +
                "alt=\"" + getProperty(ALT) + "\" " +
                "/>";
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new ImageCustomizer(snippet, target);
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
