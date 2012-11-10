/*
 * SelectCustomizer.java
 *
 * Created on January 28, 2009, 8:43 PM
 */

package org.netbeans.modules.web.stripes.palette.items;

import org.netbeans.modules.web.stripes.palette.CodeSnippet;
import org.netbeans.modules.web.stripes.palette.CodeSnippetCustomizer;

/**
 *
 * @author  Josef Sustacek
 */
public class SelectCustomizer extends CodeSnippetCustomizer {

    /** Creates new form SelectCustomizer */
    public SelectCustomizer(CodeSnippet codeSnippet) {
        super(codeSnippet);
        
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
        nameText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        size = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        multipleBox = new javax.swing.JCheckBox();
        disabledBox = new javax.swing.JCheckBox();

        jLabel1.setText(org.openide.util.NbBundle.getMessage(SelectCustomizer.class, "SelectCustomizer.jLabel1.text")); // NOI18N

        nameText.setText(org.openide.util.NbBundle.getMessage(SelectCustomizer.class, "SelectCustomizer.nameText.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(SelectCustomizer.class, "SelectCustomizer.jLabel2.text")); // NOI18N

        size.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), Integer.valueOf(1), null, Integer.valueOf(1)));

        jLabel3.setText(org.openide.util.NbBundle.getMessage(SelectCustomizer.class, "SelectCustomizer.jLabel3.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(SelectCustomizer.class, "SelectCustomizer.jLabel4.text")); // NOI18N

        multipleBox.setText(org.openide.util.NbBundle.getMessage(SelectCustomizer.class, "SelectCustomizer.multipleBox.text")); // NOI18N

        disabledBox.setText(org.openide.util.NbBundle.getMessage(SelectCustomizer.class, "SelectCustomizer.disabledBox.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(disabledBox)
                            .addComponent(multipleBox)
                            .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(multipleBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(disabledBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void evaluateInput() {
        codeSnippet.setProperty(Select.NAME, nameText.getText());
        codeSnippet.setProperty(Select.SIZE, size.getValue().toString());
        codeSnippet.setProperty(Select.MULTIPLE, multipleBox.isSelected() ? "selected" : "");
        codeSnippet.setProperty(Select.DISABLED, disabledBox.isSelected() ? "disabled" : "");
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox disabledBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JCheckBox multipleBox;
    private javax.swing.JTextField nameText;
    private javax.swing.JSpinner size;
    // End of variables declaration//GEN-END:variables

}
