/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.hyperlinks;

import java.text.MessageFormat;
import java.util.logging.Logger;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.web.stripes.util.Constants;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

/**
 *
 * @author Josef Sustacek
 */
class OpenActionBeanThread extends OpenFileThread {
    
    private static final Logger log = Logger.getLogger(Constants.LOGGER_NAME);
    
    
    
    public OpenActionBeanThread(BaseDocument srcDoc, String targetIdentifier) {
        super(srcDoc, targetIdentifier);
    }
    
    
    @Override
    public void run() {
        log.finest("Running opening of hyperlinked java file");
        
        FileObject foActionBean = findJavaFile();
        if(null == foActionBean){
            log.fine("Identifier " + targetIdentifier + " not found as a java file");
        
            String message = MessageFormat.format(
                NbBundle.getBundle(this.getClass()).getString("status.action.bean.not.found"), 
                targetIdentifier);
                    
            
            StatusDisplayer.getDefault().setStatusText(message);
            
            return;
        }
        
        open(foActionBean);
    }
    
    private FileObject findJavaFile(){
        if(null == srcDoc || null == targetIdentifier){
            return null;
        }
        
        FileObject fo = NbEditorUtilities.getFileObject(srcDoc);
        Project project = FileOwnerQuery.getOwner(fo);
        
        
        String classRelativePath = targetIdentifier.replaceAll("\\.", "/");
        FileObject classFO = null;
        
        classRelativePath +=  ".java";
        
        log.finest("Finding file " + classRelativePath);
        
        for(SourceGroup sg : ProjectUtils.getSources(project).
                getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA)){
            
            FileObject rootFO = sg.getRootFolder();
            
            classFO = rootFO.getFileObject(classRelativePath);
            
            if(null != classFO){
                break;
            }
        }
        
        log.finest("found java file object for " + targetIdentifier + ": " + classFO);
        
        return classFO;
    }
}
