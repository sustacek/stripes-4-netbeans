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
public class UseActionBean extends CodeSnippet{

    private static final String BUNDLE_KEY = "UseAcBean";
    
    static final String VAR = "var"; 
    static final String BINDING = "binding"; 
    static final String BEAN_CLASS = "beanclass"; 
    
    @Override
    protected String createBody(int caretPositionOnLine) {
        StringBuilder tag = new StringBuilder("<" + STRIPES_PREFIX + ":useActionBean");
        
        tag.append(" var=\"" + getProperty(VAR) + "\"");
        
        if(null != getProperty(BINDING)){
            tag.append(" binding=\"" + getProperty(BINDING) + "\"");
        }
        
        if(null != getProperty(BEAN_CLASS)){
            tag.append(" beanclass=\"" + getProperty(BEAN_CLASS) + "\"");
        }
        
        tag.append("/>");
        
        return tag.toString();
    }

    @Override
    protected CodeSnippetCustomizer getCustomizer(CodeSnippet snippet, JTextComponent target) {
        return new UseActionBeanCustomizer(snippet, target);
    }

    @Override
    protected String getBundleKey() {
        return BUNDLE_KEY;
    }

}
