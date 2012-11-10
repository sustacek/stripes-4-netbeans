/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.hyperlinks;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.java.lexer.JavaTokenId;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.web.stripes.util.Constants;
import org.openide.util.RequestProcessor;

/**
 *
 * @author Josef Sustacek
 */
public class StripesActionBeanToJspHyperlinkProvider extends StripesHyperlinkProvider {

    private static final Logger log = Logger.getLogger(Constants.LOGGER_NAME);
    
    private static final List<String> supportedResolutions = 
        Arrays.asList(new String[] {
            "ForwardResolution",
            "OnwardResolution",
            "RedirectResolution",
        });
    
    @Override
    public boolean isHyperlinkPoint(Document doc, int offset) {
        if(!isHyperlinkPointValid(doc)){
            return false;
        }
        
        BaseDocument bdoc = (BaseDocument) doc;
        
        try {
            
            TokenHierarchy hi = TokenHierarchy.create(
                    doc.getText(0, doc.getLength()), JavaTokenId.language());
            
            @SuppressWarnings(value="unchecked")
            TokenSequence<JavaTokenId> ts = hi.tokenSequence();
            
            ts.move(offset);
            boolean lastTokenInDocument = !ts.moveNext();

            if(lastTokenInDocument){
                // end of the document
                return false;
            }
            
            Token<JavaTokenId> identifierToken = ts.offsetToken();
            
            log.finest("Identifier: " + (null != identifierToken ? 
                identifierToken.text().toString() : "null"));
            
            do {
                // find '('
                ts.movePrevious();
            } while (ts.token() != null && 
                    ts.token().id() == JavaTokenId.WHITESPACE); // whitespace
            
            do {
                 // find any Resolution -- ForwardResolution etc.
                ts.movePrevious();
            } while (ts.token() != null
                    && ts.token().id() == JavaTokenId.WHITESPACE); // whitespace
            
            
            Token<JavaTokenId> resolutionClassToken = ts.token();
            
            
            if(null != identifierToken &&
                    null != resolutionClassToken &&
                    matchesSupportedResolutions(resolutionClassToken.text().toString()) &&
                    identifierToken.id() == JavaTokenId.STRING_LITERAL && // identified must be string
                    identifierToken.length() > 2){ // identifier must be longer than "" string
                
                lastDocument = bdoc;
                
                startOffset = identifierToken.offset(hi) + 1;
                endOffset = identifierToken.offset(hi) + identifierToken.length() - 1;

                
                if(startOffset > endOffset){
                    endOffset = startOffset;
                }
                
                identifier = identifierToken.text().
                        subSequence(1, identifierToken.length() - 1).toString();
                
                log.finer("Hyperlink at: " + startOffset + "-" + endOffset + 
                        ": " + identifier);
                
                return true;
            } else {
                return false;
            }
            
         
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        
        
        return true;
    }
    
    @Override
    public void performClickAction(Document doc, int offset) {
        if(!isPerformClickActionValid(doc)){
            return;
        }
        
        BaseDocument bdoc = (BaseDocument) doc;
        
        //Start a new thread for opening the Jsp document:
        OpenJspFileThread run = new OpenJspFileThread(bdoc, identifier);
        RequestProcessor.getDefault().post(run);
    }

    private static boolean matchesSupportedResolutions(String res){
        for(String supportedResolution: supportedResolutions) {
            if(res.contains(supportedResolution)){
                return true;
            }
        }
        
        return false;
    }
    
}
