/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.hyperlinks;

import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.editor.BaseDocument;
import org.netbeans.editor.Utilities;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProvider;

/**
 *
 * @author Josef Sustacek
 */
public abstract class StripesHyperlinkProvider implements HyperlinkProvider {
    
    protected Document lastDocument;
    protected int startOffset;
    protected int endOffset;
    protected String identifier;
    
    /**
     * Determines the length of the hyperlink.
     * 
     * @param doc
     * @param span
     * @return
     */
    @Override
    public int[] getHyperlinkSpan(Document doc, int span) {
        if(!isHyperlinkSpanValid(doc)){
            return null;
        }

        // Return the position that we defined in the isHyperlinkPoint method:
        return new int[] { startOffset, endOffset };
    }
    
    
    /**
     * Returns true if given document is valid for hyperlinking.
     * 
     * @param doc
     * @return
     */
    protected boolean isHyperlinkPointValid(Document doc){
        // We want to work only with org.netbeans.editor.BaseDocument:
        if (!(doc instanceof BaseDocument)) {
            return false;
        }

        JTextComponent target = Utilities.getFocusedComponent();

        // We want to work only with the open editor and 
        // the editor has to be the active component:
        if ((target == null) || (target.getDocument() != doc)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Returns true, if given document is valid for hyperlink span.
     * 
     * @param doc
     * @return
     */
    protected boolean isHyperlinkSpanValid(Document doc){
        // First check that we are working with BaseDocument:
        if (!(doc instanceof BaseDocument)) {
            return false;
        }

        BaseDocument bdoc = (BaseDocument) doc;
        JTextComponent target = Utilities.getFocusedComponent();

        // We want to work only with the open editor 
        // and the editor has to be the active component and
        // the document has to be the same as was used in the isHyperlinkPoint method:
        if ((target == null) || (lastDocument != bdoc)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Returns true, if given document is valid for performing the click action.
     * 
     * @param doc
     * @return
     */
    protected boolean isPerformClickActionValid(Document doc){
        // First check that we are working with BaseDocument:
        if (!(doc instanceof BaseDocument)) {
            return false;
        }

        JTextComponent target = Utilities.getFocusedComponent();

        // We want to work only with the open editor and 
        // the editor has to be active component and
        // the document has to be the same as was used in the isHyperlinkPoint method:
        if ((target == null) || (lastDocument != doc)) {
            return false;
        }
        
        return true;
    }
}
