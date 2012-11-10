/*
 * ImageCustomizer.java
 *
 * Created on January 25, 2009, 2:23 PM
 */

package org.netbeans.modules.web.stripes.palette.items;

import javax.swing.text.JTextComponent;

import org.netbeans.modules.web.stripes.palette.CodeSnippet;
import org.netbeans.modules.web.stripes.palette.CodeSnippetCustomizer;
import org.netbeans.modules.web.stripes.palette.StripesPaletteUtilities;
import org.netbeans.modules.web.stripes.util.StringUtils;
import org.netbeans.modules.web.stripes.util.browse.BrowseDialogs;

/**
 *
 * @author  Josef Sustacek
 */
public class ImageCustomizer extends CodeSnippetCustomizer {

    private JTextComponent target;
    
    /** Creates new form ImageCustomizer */
    public ImageCustomizer(CodeSnippet codeSnippet, JTextComponent target) {
        super(codeSnippet);
 
        this.target = target;
        
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        srcText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        altText = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        browseBtn = new javax.swing.JButton();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(ImageCustomizer.class, "ImageCustomizer.jLabel1.text")); // NOI18N

        srcText.setText(org.openide.util.NbBundle.getMessage(ImageCustomizer.class, "ImageCustomizer.srcText.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(ImageCustomizer.class, "ImageCustomizer.jLabel2.text")); // NOI18N

        altText.setText(org.openide.util.NbBundle.getMessage(ImageCustomizer.class, "ImageCustomizer.altText.text")); // NOI18N

        jLabel3.setText(org.openide.util.NbBundle.getMessage(ImageCustomizer.class, "ImageCustomizer.jLabel3.text")); // NOI18N

        nameText.setText(org.openide.util.NbBundle.getMessage(ImageCustomizer.class, "ImageCustomizer.nameText.text")); // NOI18N

        browseBtn.setText(org.openide.util.NbBundle.getMessage(ImageCustomizer.class, "ImageCustomizer.browseBtn.text")); // NOI18N
        browseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(altText)
                    .addComponent(nameText)
                    .addComponent(srcText, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(browseBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(browseBtn)
                    .addComponent(srcText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(altText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void browseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseBtnActionPerformed
    String imagePath = BrowseDialogs.imageSourcesDialog(
            StripesPaletteUtilities.getProject(target), this, 
            new String[] {"jpg", "jpeg", "gif", "png", "bmp"});
    
    if(!StringUtils.nullOrEmpty(imagePath)){
        srcText.setText(imagePath);
    }
}//GEN-LAST:event_browseBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField altText;
    private javax.swing.JButton browseBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField nameText;
    private javax.swing.JTextField srcText;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void evaluateInput() {
        codeSnippet.setProperty(Image.NAME, nameText.getText());
        codeSnippet.setProperty(Image.SRC, srcText.getText());
        codeSnippet.setProperty(Image.ALT, altText.getText());
    }
}
