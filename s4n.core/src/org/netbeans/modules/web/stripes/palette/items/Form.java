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
public class Form extends CodeSnippet{

    private static final String BUNDLE_KEY = "Form";
    
    static final String ACTION = "action";
    static final String BEAN_CLASS = "beanclass";
    static final String METHOD = "method";
    
    private static final String END_TAG = "</" + STRIPES_PREFIX + ":form>";
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        StringBuilder tag = new StringBuilder("<" + STRIPES_PREFIX + ":form");
        
        if(null != getProperty(ACTION)){
            tag.append(" action=\"" + getProperty(ACTION) + "\"");
        }
        
        if(null != getProperty(BEAN_CLASS)){
            tag.append(" beanclass=\"" + getProperty(BEAN_CLASS) + "\"");
        }
        
        if(!StringUtils.nullOrEmpty(getProperty(METHOD))){
            tag.append(" method=\"" + getProperty(METHOD) + "\"");
        }
        
        tag.append(">\n");
        tag.append(indent("\n", caretPositionOnLine));
        
        tag.append(indent(END_TAG, caretPositionOnLine));
        
        return tag.toString();
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new FormCustomizer(snippet, target);
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
