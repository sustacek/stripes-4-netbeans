/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.hyperlinks;

import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.html.lexer.HTMLTokenId;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.api.lexer.TokenUtilities;
import org.netbeans.editor.BaseDocument;
//import org.netbeans.editor.ext.html.HTMLTokenContext;
import org.netbeans.modules.web.stripes.util.Constants;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Josef Sustacek
 */
public class StripesBeanClassHyperlinkProvider extends StripesHyperlinkProvider {

    private static final Logger log = Logger.getLogger(Constants.LOGGER_NAME);
    
    private static String SBEANCLASS_IDENTIFIER = "beanclass";
    

    public StripesBeanClassHyperlinkProvider() {
        this.lastDocument = null;
    }
    
    
    /**
     * Determines whether there should be a hyperlink at the given offset within 
     * the given document. The inline comments in the method below, as well 
     * as in the code in the remainder of this tutorial, serve to explain 
     * the purpose of the code.
     * 
     * @param doc
     * @param offset
     * @return
     */
    @Override
    public boolean isHyperlinkPoint(Document doc, int offset) {
        if(!isHyperlinkPointValid(doc)){
            return false;
        }

        BaseDocument bdoc = (BaseDocument) doc;
        
        try {
            
            TokenHierarchy hi = TokenHierarchy.create(
                    doc.getText(0, doc.getLength()), HTMLTokenId.language());
            
            @SuppressWarnings(value="unchecked")
            TokenSequence<HTMLTokenId> ts = hi.tokenSequence();
            
            ts.move(offset);
            boolean lastTokenInDocument = !ts.moveNext();

            if(lastTokenInDocument){
                // end of the document
                return false;
            }
            
            Token<HTMLTokenId> identifierToken = ts.offsetToken();
            
            do {
                // find '='
                ts.movePrevious();
            } while (ts.token() != null && 
                    ts.token().id() == HTMLTokenId.WS); // whitespace
            
            do {
                 // find 'beanclass'
                ts.movePrevious();
            } while (ts.token() != null
                    && ts.token().id() == HTMLTokenId.WS); // whitespace
            
            
            Token<HTMLTokenId> beanclassAttrToken = ts.token();
            
            
            if(null != identifierToken &&
                    null != beanclassAttrToken &&
                    SBEANCLASS_IDENTIFIER.equals( // attribute must be "beanclass"
                        beanclassAttrToken.text().toString()) &&
                    identifierToken.id() == HTMLTokenId.VALUE && // identified must be value of the attribute
                    identifierToken.length() > 2){ // identifier must be longer than "" string
                
                lastDocument = bdoc;
                
                startOffset = identifierToken.offset(hi) + 1;
                endOffset = identifierToken.offset(hi) + identifierToken.length() - 1;

                
                if(startOffset > endOffset){
                    endOffset = startOffset;
                }
                
                identifier = identifierToken.text().
                        subSequence(1, identifierToken.length() - 1).toString();
                
                log.finest("Hyperlink at: " + startOffset + "-" + endOffset + 
                        ": " + identifier);
                
                return true;
            } else {
                return false;
            }
            
         
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * Determines what happens when the hyperlink is clicked. In general, 
     * a document should open, the cursor should move to a certain place 
     * in a document, or both.
     * 
     * @param arg0
     * @param arg1
     */
    @Override
    public void performClickAction(Document doc, int offset) {
        if(!isPerformClickActionValid(doc)){
            return;
        }

        BaseDocument bdoc = (BaseDocument) doc;
        
        //Start a new thread for opening the Java document:
        OpenActionBeanThread run = new OpenActionBeanThread(bdoc, identifier);
        RequestProcessor.getDefault().post(run);
    }

}
