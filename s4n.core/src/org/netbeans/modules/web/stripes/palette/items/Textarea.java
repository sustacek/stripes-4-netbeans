/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.palette.items;

import org.netbeans.modules.web.stripes.palette.CodeSnippet;
import org.netbeans.modules.web.stripes.palette.CodeSnippetCustomizer;
import javax.swing.text.JTextComponent;
import org.netbeans.modules.web.stripes.util.StringUtils;

/**
 *
 * @author Josef Sustacek
 */
public class Textarea extends Input {

    private static final String BUNDLE_KEY = "Textarea";
    
    static final String ROWS = "rows";
    static final String COLLS = "colls";
    static final String DISABLED = "disabled";
    static final String READONLY = "readonly";
    
    
    private static final String END_TAG = "</" + STRIPES_PREFIX + ":textarea>";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":textarea " +
                "name=\"" + getProperty(NAME) + "\" " + 
                "rows=\"" + getProperty(ROWS) + "\" " + 
                "colls=\"" + getProperty(COLLS) + "\" " + 
                (!StringUtils.nullOrEmpty(getProperty(DISABLED)) ? 
                    "disabled=\"disabled\" " : "") +
                (!StringUtils.nullOrEmpty(getProperty(READONLY)) ? 
                    "readonly=\"readonly\" " : "") +    
                    
                ">" +
                
                END_TAG;
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new TextareaCustomizer(snippet);
    }

//    @Override
//    int getCaretShift(int caretIndent) {
//        return (-1) * END_TAG.length();
//    }
    
    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
