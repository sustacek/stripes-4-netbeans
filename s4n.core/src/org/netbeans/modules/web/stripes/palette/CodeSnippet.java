/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.palette;

import java.util.HashMap;
import java.util.Map;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.NbBundle;

/**
 *
 * @author Josef Sustacek
 */
public abstract class CodeSnippet implements ActiveEditorDrop{
    
    public static final String STRIPES_URI = "http://stripes.sourceforge.net/stripes.tld";
    public static final String STRIPES_PREFIX = "s";
    
    private Map<String, String> properties = new HashMap<String, String>(5);
    
    /**
     * Sets the property of given name and given value for this code snippet.
     * 
     * @param name
     * @param value
     */
    public void setProperty(String name, String value){
        properties.put(name, value);
    }
    
    /**
     * Gets the property of given name for this code snippet and returns its 
     * value, if any.
     * 
     * @param name
     * @return
     */
    public String getProperty(String name){
        return properties.get(name);
    }

    /**
     * Clears all properties registered for this code snippet.
     * 
     */
    public void clearProperties(){
        properties.clear();
    }
    
    /**
     * This method handles the placement of code snippet from the palette to
     * given target component.
     * 
     * @param targetComponent
     * @return
     */
    public boolean handleTransfer(JTextComponent targetComponent) {
        CodeSnippetCustomizer customizer = getCustomizer(this, targetComponent);
        boolean accept = (null != customizer ? 
            customizer.showDialog(getDialogTitle()) : 
            true);
        if (accept) {
            int indentCount = StripesPaletteUtilities.getCaretIndent(targetComponent);
            
            String body = createBody(indentCount);
            
            try {
                StripesPaletteUtilities.insert(body, targetComponent, getCaretShift(indentCount));
            } catch (BadLocationException ble) {
                accept = false;
            }
        }
        return accept;
    }
    
    /**
     * Indents given string with appropriate count of spaces.
     * 
     * @param stringToIndent
     * @param indentCount
     * @return
     */
    protected String indent(String stringToIndent, int indentCount){
        StringBuilder indentedValue = new StringBuilder();
        for(int i=0;i<indentCount;i++){
            indentedValue.append(" ");
        }
        
        indentedValue.append(stringToIndent);
        
        return indentedValue.toString();
    }
    
    /**
     * Returns localized title of dialog, which may be raised for customization 
     * of inserted code snippet.
     * 
     * @return
     */
    protected String getDialogTitle() {
        String displayName = "";
        try {
            displayName = NbBundle.getBundle(getClass()).
                    getString("NAME_stripes-" + getBundleKey()); // NOI18N
        } catch (Exception e) {}
        
        String title = NbBundle.getMessage(getClass(), 
                        "LBL_Customizer_InsertPrefix") + " " + displayName;
        
        return title;
    }
    
    /**
     * Positive or negative number determining, that the cater should be 
     * moved forward or backward after insertion of the snippet code.
     * 
     * Enables the caret to be placed directly on the place of the tag, where
     * is next input expected.
     * 
     * @return
     */
    protected int getCaretShift(int caretIndent){
        return 0;
    }
    
    /**
     * Returns the string reprezentation of that will be inserted into the code.
     * Typically "&lt;s:link /&gt; or similar.
     * 
     * @param caretPositionOnLine
     * @return
     */
    protected abstract String createBody(int caretPositionOnLine);
    
    /**
     * Returns an extension of CodeSnippetCustomizer that serves as a visual 
     * customization of inserted code snippet and enables to add dynamic 
     * values to inserted code, like value of "href" attribute of "link" tag.
     * 
     * @param snippet
     * 
     * @return visual component for customizing the inserted code of null, if
     * no one is needed
     */
    protected abstract CodeSnippetCustomizer getCustomizer(
            CodeSnippet snippet, JTextComponent target);
    
    /**
     * Returns the unique identifier for some recourse bundle keys needed for
     * this code snippet.
     * @return
     */
    protected abstract String getBundleKey();
}
