/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.hyperlinks;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JEditorPane;
import org.netbeans.editor.BaseDocument;
import org.netbeans.editor.Utilities;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;

/**
 *
 * @author Josef Sustacek
 */
public abstract class OpenFileThread implements Runnable{
    
    protected BaseDocument srcDoc;
    protected String targetIdentifier;

    public OpenFileThread(BaseDocument srcDoc, String targetIdentifier) {
        this.srcDoc = srcDoc;
        this.targetIdentifier = targetIdentifier;
    }
    
    
    /**
     * Opens given file object in Netbeans editor.
     * 
     * @param fo
     */
    protected void open(FileObject fo){
        //Here we're finding our Bean class file:
        DataObject dObject;
        try {
            dObject = DataObject.find(fo);
            
            final EditorCookie.Observable ec = dObject.getCookie(
                    EditorCookie.Observable.class);
            
            if (ec != null) {
                Utilities.runInEventDispatchThread(new Runnable() {
                    public void run() {
                        final JEditorPane[] panes = ec.getOpenedPanes();
                        
                        //Here we're positioning the cursor,
                        //if the document isn't open, we need to open it first:
                        if ((panes != null) && (panes.length > 0)) {
                            setPosition(panes[0]);
                        } else {
                            ec.addPropertyChangeListener(new PropertyChangeListener() {
                                public void propertyChange(PropertyChangeEvent evt) {
                                    if (EditorCookie.Observable.
                                      PROP_OPENED_PANES.equals(evt.getPropertyName())) {
                                        final JEditorPane[] panes = ec.getOpenedPanes();
                                        if ((panes != null) && (panes.length > 0)) {
                                            setPosition(panes[0]);
                                        }
                                        ec.removePropertyChangeListener(this);
                                    }
                                }
                            });
                        }
                        
                        ec.open();
                    }
                });
            }
        } catch (DataObjectNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public abstract void run();
    
    /**
     * Override this method when a special cater position
     * in opened file is desired.
     * 
     * @param pane
     */
    protected void setPosition(JEditorPane pane) {
        
        
//        try {
//            //The whole text:
//            String text = pane.getDocument().getText(0, 
//                 pane.getDocument().getLength() - 1);
//            //The place where we want the cursor to be:
//            int index = text.indexOf("<h2>");
//            //If we can find it, we place the cursor there:
//            if (index > 0) {
//                pane.setCaretPosition(index);
//            } 
//        } catch (BadLocationException ex) {
//            ex.printStackTrace();
//        }
    }
}
