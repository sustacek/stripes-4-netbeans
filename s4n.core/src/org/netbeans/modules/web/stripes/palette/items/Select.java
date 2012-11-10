/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.palette.items;

import javax.swing.text.JTextComponent;
import org.netbeans.modules.web.stripes.palette.CodeSnippet;
import org.netbeans.modules.web.stripes.palette.CodeSnippetCustomizer;
import org.netbeans.modules.web.stripes.util.StringUtils;

/**
 *
 * @author Josef Sustacek
 */
public class Select extends Input {

    private static final String BUNDLE_KEY = "Select";
    private static final String END_TAG = "</" + STRIPES_PREFIX + ":select>";
    
    static final String SIZE = "size";
    static final String MULTIPLE = "multiple";
    static final String DISABLED = "disabled";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        return "<" + STRIPES_PREFIX + ":select " +
                "name=\"" + getProperty(NAME) + "\" " + 
                "size=\"" + getProperty(SIZE) + "\" " + 
                (!StringUtils.nullOrEmpty(getProperty(DISABLED)) ? 
                    "disabled=\"disabled\" " : "") +
                (!StringUtils.nullOrEmpty(getProperty(MULTIPLE)) ? 
                    "multiple=\"multiple\" " : "") +    
                
                ">\n" +
                
                indent("\n", caretPositionOnLine) +
        
                indent(END_TAG, caretPositionOnLine);
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new SelectCustomizer(snippet);
    }
    
    @Override
    protected int getCaretShift(int caretIndent) {
        return (-1) * (END_TAG.length() + 1 + caretIndent);
    }
    
    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
