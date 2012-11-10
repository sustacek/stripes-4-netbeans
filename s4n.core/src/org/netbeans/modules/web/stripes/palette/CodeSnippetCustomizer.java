/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.palette;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import org.netbeans.modules.web.stripes.util.StringUtils;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.NbBundle;

/**
 *
 * @author Josef Sustacek
 */
public abstract class CodeSnippetCustomizer extends JPanel{

    private Dialog dialog = null;
    private DialogDescriptor descriptor = null;
    private boolean dialogOK = false;
    
    protected CodeSnippet codeSnippet;
    
    protected CodeSnippetCustomizer(CodeSnippet codeSnippet) {
        this.codeSnippet = codeSnippet;
    }  
    
    protected boolean showDialog(String dialogTitle) {

        if(StringUtils.nullOrEmpty(dialogTitle)){
            dialogTitle = NbBundle.
                    getMessage(getClass(), "default.dialog.title");        }
        
        dialogOK = false;

        descriptor = new DialogDescriptor(this, dialogTitle,  true,
                DialogDescriptor.OK_CANCEL_OPTION, 
                DialogDescriptor.OK_OPTION,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (descriptor.getValue().equals(DialogDescriptor.OK_OPTION)) {
                            evaluateInput();
                            dialogOK = true;
                        }
                        dialog.dispose();
                    }
                }
        );

        dialog = DialogDisplayer.getDefault().createDialog(descriptor);
        dialog.setVisible(true);
        repaint();

        return dialogOK;

    }
    
    /**
     * This method collect all needed values from visual panel and puts it into 
     * codeSnippet properties.
     */
   protected abstract void evaluateInput();
}   
