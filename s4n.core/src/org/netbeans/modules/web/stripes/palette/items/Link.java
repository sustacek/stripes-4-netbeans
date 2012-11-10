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
public class Link extends CodeSnippet {

    private static final String BUNDLE_KEY = "Link";
    private static final String END_TAG = "</" + STRIPES_PREFIX + ":link>";
    
    static final String HREF = "href"; 
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        String tag = "<" + STRIPES_PREFIX + ":link href=\"" + 
                    getProperty(HREF) + "\" >" +
                    
                    END_TAG;
        
        return tag;
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new LinkCustomizer(snippet);
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
