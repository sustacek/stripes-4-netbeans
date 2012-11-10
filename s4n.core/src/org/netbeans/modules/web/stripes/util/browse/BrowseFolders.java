/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.modules.web.stripes.util.browse;
import java.io.File;
import java.io.FileFilter;
import org.netbeans.api.project.SourceGroup;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.netbeans.modules.web.stripes.palette.StripesPaletteUtilities;
import org.openide.util.NbBundle;

/**
 * @author pfiala
 */
@SuppressWarnings("unchecked")
public class BrowseFolders extends JPanel implements ExplorerManager.Provider {

    private ExplorerManager manager;

    private static JScrollPane SAMPLE_SCROLL_PANE = new JScrollPane();
    private FileFilter filter;

    public static final FileFilter imageFileFilter = new FileFilter() {
        public boolean accept(File pathname) {
            return FileUtil.toFileObject(pathname).getMIMEType().startsWith("image/"); // NOI18N
        }
    };
    
    
    /**
     * Creates new form BrowseFolders
     */
    public BrowseFolders(SourceGroup[] folders, FileFilter filter) {
        initComponents();
        
        this.filter = filter;
        if (this.filter == null) {
            this.filter = new FileFilter() {
                public boolean accept(File pathname) {
                    return true;
                }
            };
        }
        
        manager = new ExplorerManager();
        AbstractNode rootNode = new AbstractNode(new SourceGroupsChildren(folders));
        manager.setRootContext(rootNode);
        
        // Create the templates view
        BeanTreeView btv = new BeanTreeView();
        btv.setRootVisible(false);
        btv.setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
        btv.setBorder(SAMPLE_SCROLL_PANE.getBorder());
        btv.setDefaultActionAllowed(false);
        expandFirstLevel(btv);
        folderPanel.add(btv, java.awt.BorderLayout.CENTER);
    }
        
    // ExplorerManager.Provider implementation ---------------------------------
    
    public ExplorerManager getExplorerManager() {
        return manager;
    }

    private void expandFirstLevel(BeanTreeView btv) {
        
        Node root = manager.getRootContext();
        Children ch = root.getChildren();
        if ( ch == Children.LEAF ) {
            return;
        }
        Node nodes[] = ch.getNodes(true);

        btv.expandNode(root);
        for ( int i = 0; i < nodes.length; i++ ) {            
            btv.expandNode( nodes[i] );
            if ( i == 0 ) {
                try {
                    manager.setSelectedNodes( new Node[] { nodes[i] } );
                }
                catch ( java.beans.PropertyVetoException e ) {
                    // No selection for some reason
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        folderPanel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(org.openide.util.NbBundle.getMessage(BrowseFolders.class, "LBL_Folders")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 2, 0);
        add(jLabel1, gridBagConstraints);

        folderPanel.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(folderPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel folderPanel;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
        
    public static FileObject showDialog(SourceGroup[] folders) {
        return showDialog(folders, null);
    }

    static class TreeMouseAdapter extends MouseAdapter {
        
        JTree tree;
        BrowseFolders bf;
        JButton[] options;
        
        TreeMouseAdapter(JTree tree, BrowseFolders bf, JButton[] options) {
            this.tree = tree;
            this.bf = bf;
            this.options = options;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            int selRow = tree.getRowForLocation(e.getX(), e.getY());

            if ((selRow != -1) && SwingUtilities.isLeftMouseButton(e) && (e.getClickCount() % 2) == 0) {
                FileObject fileObject = bf.getSelectedFileObject();
                if (fileObject != null && !fileObject.isFolder())
                    options[0].doClick();
            }
        }
    }

    public static FileObject showDialog(SourceGroup[] folders, FileFilter filter) {

        final BrowseFolders bf = new BrowseFolders(folders, filter);

        final JButton options[] = new JButton[]{
            new JButton(NbBundle.getMessage(BrowseFolders.class, "LBL_SelectFile")),
            new JButton(NbBundle.getMessage(BrowseFolders.class, "LBL_Cancel")),
        };

        options[0].setEnabled(false);

        JTree tree = StripesPaletteUtilities.findTreeComponent(bf);
        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                FileObject fileObject = bf.getSelectedFileObject();
                options[0].setEnabled(fileObject != null && !fileObject.isFolder());
            }
        });
        
        OptionsListener optionsListener = new OptionsListener(bf);

        options[0].setActionCommand(OptionsListener.COMMAND_SELECT);
        options[0].addActionListener(optionsListener);
        options[1].setActionCommand(OptionsListener.COMMAND_CANCEL);
        options[1].addActionListener(optionsListener);
        
        MouseListener ml = new TreeMouseAdapter(tree, bf, options);
        tree.addMouseListener(ml);


        DialogDescriptor dialogDescriptor = new DialogDescriptor(bf, // innerPane
                NbBundle.getMessage(BrowseFolders.class, "LBL_BrowseFiles"), // displayName
                true, // modal
                options, // options
                options[0], // initial value
                DialogDescriptor.BOTTOM_ALIGN, // options align
                null, // helpCtx
                null);                                 // listener

        dialogDescriptor.setClosingOptions(new Object[]{options[0], options[1]});

        Dialog dialog = DialogDisplayer.getDefault().createDialog(dialogDescriptor);
        dialog.setVisible(true);

        return optionsListener.getResult();

    }


    private FileObject getSelectedFileObject() {
        Node selection[] = getExplorerManager().getSelectedNodes();
        if (selection != null && selection.length > 0) {
            DataObject dobj = selection[0].getLookup().lookup(DataObject.class);
            return dobj.getPrimaryFile();
        }
        return null;
    }

    // Innerclasses ------------------------------------------------------------
    
    /**
     * Children to be used to show FileObjects from given SourceGroups
     */

    private final class SourceGroupsChildren extends Children.Keys {

        private SourceGroup[] groups;
        private SourceGroup group;
        private FileObject fo;

        public SourceGroupsChildren(SourceGroup[] groups) {
            this.groups = groups;
        }

        public SourceGroupsChildren(FileObject fo, SourceGroup group) {
            this.fo = fo;
            this.group = group;
        }

        @Override
        protected void addNotify() {
            super.addNotify();
            setKeys(getKeys());
        }

        @Override
        protected void removeNotify() {
            setKeys(Collections.EMPTY_SET);
            super.removeNotify();
        }

        protected Node[] createNodes(Object key) {

            FileObject fObj = null;
            SourceGroup grp = null;
            boolean isFile = false;

            if (key instanceof SourceGroup) {
                fObj = ((SourceGroup) key).getRootFolder();
                grp = (SourceGroup) key;
            } else if (key instanceof Key) {
                fObj = ((Key) key).folder;
                grp = ((Key) key).group;
                if (!fObj.isFolder()) {
                    isFile = true;
                }
            }

            try {
                DataObject dobj = DataObject.find(fObj);
                FilterNode fn = (isFile ?
                        new FilterNode(dobj.getNodeDelegate(), Children.LEAF) :
                        new FilterNode(dobj.getNodeDelegate(), new SourceGroupsChildren(fObj, grp)));

                if (key instanceof SourceGroup) {
                    fn.setDisplayName(grp.getDisplayName());
                }

                return new Node[]{fn};
            } catch (DataObjectNotFoundException e) {
                return null;
            }
        }

        private Collection getKeys() {

            if (groups != null) {
                return Arrays.asList(groups);
            } else {
                FileObject files[] = fo.getChildren();
                Arrays.sort(files, new FileObjectComparator());
                ArrayList children = new ArrayList(files.length);
                for (int i = 0; i < files.length; i++) {
                    FileObject file = files[i];
                    if (group.contains(files[i]) && file.isFolder()) {
                        children.add(new Key(files[i], group));
                    }
                }
                // add files
                for (int i = 0; i < files.length; i++) {
                    FileObject file = files[i];
                    if (group.contains(file) && !files[i].isFolder()) {
                        if (filter.accept(FileUtil.toFile(file))) {
                            children.add(new Key(files[i], group));
                        }
                    }
                }
                //}
                
                return children;
            }

        }

        private class Key {

            private FileObject folder;
            private SourceGroup group;

            private Key(FileObject folder, SourceGroup group) {
                this.folder = folder;
                this.group = group;
            }

        }

    }

    private class FileObjectComparator implements java.util.Comparator {
        public int compare(Object o1, Object o2) {
            FileObject fo1 = (FileObject) o1;
            FileObject fo2 = (FileObject) o2;
            return fo1.getName().compareTo(fo2.getName());
        }
    }

    private static final class OptionsListener implements ActionListener {

        public static final String COMMAND_SELECT = "SELECT"; //NOI18N
        public static final String COMMAND_CANCEL = "CANCEL"; //NOI18N

        private BrowseFolders browsePanel;

        private FileObject result;
        //private Class target;
        
        public OptionsListener(BrowseFolders browsePanel) {
            this.browsePanel = browsePanel;
        }

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (COMMAND_SELECT.equals(command)) {
                this.result = browsePanel.getSelectedFileObject();
            }
        }

        public FileObject getResult() {
            return result;
        }
    }

}
