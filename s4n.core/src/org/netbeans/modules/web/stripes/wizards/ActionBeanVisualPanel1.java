/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.wizards;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.netbeans.modules.web.stripes.util.StringUtils;
import org.netbeans.modules.web.stripes.util.Utils;
import org.netbeans.modules.web.stripes.util.browse.BrowseDialogs;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;

public final class ActionBeanVisualPanel1 extends JPanel {

    private WizardDescriptor wizard;
    private ActionBeanWizardPanel wizardPanel;
    
    private List<AvailableClass> beanClasses = new ArrayList<AvailableClass>(3);
    private List<AvailableClass> contextClasses = new ArrayList<AvailableClass>(3);
    
    {
        initAvailableClasses(); 
    }
    
    public ActionBeanVisualPanel1(
            WizardDescriptor wizard, ActionBeanWizardPanel wizardPanel) {
        
        this.wizard = wizard;
        this.wizardPanel = wizardPanel;
        
        initComponents();
    }

    @Override
    public String getName() {
        return "Class extensions";
    }

    private AvailableClass[] getAvailableContextClasses() {
        return contextClasses.toArray(new AvailableClass[]{});   
    }
    
    private AvailableClass[] getAvailableExtendableClasses() {
        return beanClasses.toArray(new AvailableClass[] {});
    }
    
    public static class AvailableClass {
        private String packageName;
        private String className;

        public AvailableClass(String packageName, String className) {
            this.packageName = packageName;
            this.className = className;
        }

        public String getClassName() {
            return className;
        }

        public String getPackageName() {
            return packageName;
        }
        
        public String getFullName() {
            if(StringUtils.nullOrEmpty(packageName))
                return className;
            else {
                return packageName + "." + className;
            }
        }

        @Override
        public String toString() {
            return getFullName();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final AvailableClass other = (AvailableClass) obj;
            if (!this.packageName.equals(other.packageName) && 
                    (this.packageName == null || !this.packageName.equals(other.packageName))) {
                return false;
            }
            if (!this.className.equals(other.className) && 
                    (this.className == null || !this.className.equals(other.className))) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 73 * hash + (this.packageName != null ? this.packageName.hashCode() : 0);
            hash = 73 * hash + (this.className != null ? this.className.hashCode() : 0);
            return hash;
        }

        
    }
    
    
    
    public AvailableClass getExtendedActionBeanClass(){
        return (AvailableClass)extendsClassComboBox.getSelectedItem();
    }
    
    public AvailableClass getExtendedContextClass(){
        return (AvailableClass)contextClassComboBox.getSelectedItem();
    }
    
    public boolean overrideContextMethods(){
        return overrideContext.isSelected();
    }
    
    public boolean extendExistingActionBean(){
        return extendActionBean.isSelected();
    }
   
    public boolean useCustomUrlBinding(){
        return useCustomUrlBinding.isSelected();
    }
    
    public String getCustomUrlBinding(){
        return urlBinding.getText();
    }
    
    public boolean isInputValid(){
        String binding = getCustomUrlBinding();
        
        
        
        // TODO validate pattern based on value in web.xml
        
        return !StringUtils.nullOrEmpty(binding) &&
                binding.length() > 1 &&
                binding.startsWith("/");
    }

    private void initAvailableClasses() {
        contextClasses.add(
                new AvailableClass("net.sourceforge.stripes.action", "ActionBeanContext"));
        
        
        
        // TODO scan source folder for more files
        ////////// try out
//        Project p = Templates.getProject(wizard);
//        Sources s = ProjectUtils.getSources(p);
//        SourceGroup[] groups = s.getSourceGroups(Sources.TYPE_GENERIC);
//        for(SourceGroup sg: groups){
//            FileObject root = sg.getRootFolder(); 
//            ClasspathInfo cpi = ClasspathInfo.create(root);
//            ClassIndex ci = cpi.getClassIndex();
//            
//            Set<ClassIndex.SearchKind> kinds = new HashSet<ClassIndex.SearchKind>(1);
//            kinds.add(ClassIndex.SearchKind.IMPLEMENTORS);
//            
//            Set<ClassIndex.SearchScope> scopes = new HashSet<ClassIndex.SearchScope>(1);
//            scopes.add(ClassIndex.SearchScope.SOURCE);
//            
//            JavaSource javaSource = JavaSource.forFileObject(root);
//            
//            
//            ClassPath cp = cpi.getClassPath(ClasspathInfo.PathKind.SOURCE);
//            
////            Element el = JavaDataSupport.createJavaNode(root);
////            
//////            Element el = JavaSource.
////            
////            Set<FileObject> = ci.getResources(el, kinds, scopes);
//        }
        ////////// try out
    }
    
//    public JFileChooser getFileChooser(){
//        return new JFileChooser
//    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        extendsClassComboBox = new javax.swing.JComboBox();
        contextClassComboBox = new javax.swing.JComboBox();
        extendActionBean = new javax.swing.JCheckBox();
        overrideContext = new javax.swing.JCheckBox();
        useCustomUrlBinding = new javax.swing.JCheckBox();
        urlBinding = new javax.swing.JTextField();
        browseBeanBtn = new javax.swing.JButton();
        browseContextClassBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        extendsClassComboBox.setModel(new DefaultComboBoxModel(getAvailableExtendableClasses()));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, extendActionBean, org.jdesktop.beansbinding.ELProperty.create("${selected}"), extendsClassComboBox, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        contextClassComboBox.setModel(new DefaultComboBoxModel(getAvailableContextClasses()));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, overrideContext, org.jdesktop.beansbinding.ELProperty.create("${selected}"), contextClassComboBox, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(extendActionBean, org.openide.util.NbBundle.getMessage(ActionBeanVisualPanel1.class, "ActionBeanVisualPanel1.extendActionBean.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, extendsClassComboBox, org.jdesktop.beansbinding.ELProperty.create("${selectedItem != null}"), extendActionBean, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        overrideContext.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(overrideContext, org.openide.util.NbBundle.getMessage(ActionBeanVisualPanel1.class, "ActionBeanVisualPanel1.overrideContext.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(useCustomUrlBinding, org.openide.util.NbBundle.getMessage(ActionBeanVisualPanel1.class, "ActionBeanVisualPanel1.useCustomUrlBinding.text")); // NOI18N

        urlBinding.setText(org.openide.util.NbBundle.getMessage(ActionBeanVisualPanel1.class, "ActionBeanVisualPanel1.urlBinding.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, useCustomUrlBinding, org.jdesktop.beansbinding.ELProperty.create("${selected}"), urlBinding, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        urlBinding.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                urlBindingKeyReleased(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(browseBeanBtn, org.openide.util.NbBundle.getMessage(ActionBeanVisualPanel1.class, "ActionBeanVisualPanel1.browseBeanBtn.text")); // NOI18N
        browseBeanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseBeanBtnActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(browseContextClassBtn, org.openide.util.NbBundle.getMessage(ActionBeanVisualPanel1.class, "ActionBeanVisualPanel1.browseContextClassBtn.text")); // NOI18N
        browseContextClassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseContextClassBtnActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ActionBeanVisualPanel1.class, "ActionBeanVisualPanel1.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ActionBeanVisualPanel1.class, "ActionBeanVisualPanel1.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(useCustomUrlBinding)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(extendsClassComboBox, 0, 355, Short.MAX_VALUE)
                                    .addComponent(urlBinding, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(browseBeanBtn)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(extendActionBean)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(contextClassComboBox, 0, 355, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(browseContextClassBtn)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(overrideContext)
                        .addGap(183, 183, 183))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(268, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(319, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(overrideContext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browseContextClassBtn)
                    .addComponent(contextClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(extendActionBean)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(extendsClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseBeanBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(useCustomUrlBinding)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urlBinding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

private void urlBindingKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_urlBindingKeyReleased
    wizardPanel.fireChangeEvent();
}//GEN-LAST:event_urlBindingKeyReleased

private void browseContextClassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseContextClassBtnActionPerformed
    String contextClassFQN = BrowseDialogs.actionBeanContextsDilaog(
            Templates.getProject(wizard), this);
    
    if(!StringUtils.nullOrEmpty(contextClassFQN)){
        AvailableClass newCC = Utils.getClass(contextClassFQN);
        
        if(!contextClasses.contains(newCC))
            contextClasses.add(newCC);
        
        contextClassComboBox.setModel(new DefaultComboBoxModel(getAvailableContextClasses()));
        contextClassComboBox.setSelectedItem(newCC);
    }
}//GEN-LAST:event_browseContextClassBtnActionPerformed

private void browseBeanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseBeanBtnActionPerformed
    String beanFQN = BrowseDialogs.actionBeansDialog(
            Templates.getProject(wizard), this);
    
    if(!StringUtils.nullOrEmpty(beanFQN)){
        AvailableClass newExtendableBean = Utils.getClass(beanFQN);
        
        if(!beanClasses.contains(newExtendableBean))
            beanClasses.add(newExtendableBean);
        
        extendsClassComboBox.setModel(new DefaultComboBoxModel(getAvailableExtendableClasses()));
        extendsClassComboBox.setSelectedItem(newExtendableBean);
        extendActionBean.setSelected(true);
    }
}//GEN-LAST:event_browseBeanBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseBeanBtn;
    private javax.swing.JButton browseContextClassBtn;
    private javax.swing.JComboBox contextClassComboBox;
    private javax.swing.JCheckBox extendActionBean;
    private javax.swing.JComboBox extendsClassComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JCheckBox overrideContext;
    private javax.swing.JTextField urlBinding;
    private javax.swing.JCheckBox useCustomUrlBinding;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}

