/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.hyperlinks;

import java.text.MessageFormat;
import java.util.logging.Logger;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.editor.BaseDocument;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.web.stripes.palette.StripesPaletteUtilities;
import org.netbeans.modules.web.stripes.util.Constants;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.util.NbBundle;

/**
 *
 * @author Josef Sustacek
 */
public class OpenJspFileThread extends OpenFileThread {

    private static final Logger log = Logger.getLogger(Constants.LOGGER_NAME);

    public OpenJspFileThread(BaseDocument srcDoc, String targetIdentifier) {
        super(srcDoc, targetIdentifier);
    }
    
    
    
    @Override
    public void run() {
        log.finest("Running opening of hyperlinked jsp file");
        
        
        FileObject jspFO = findJspFile();
        
        if(null == jspFO){
            log.fine("Identifier " + targetIdentifier + " not found as a jsp file");
            
            String message = MessageFormat.format(
                NbBundle.getBundle(this.getClass()).getString("status.jsp.not.found"), 
                targetIdentifier);
                    
            
            StatusDisplayer.getDefault().setStatusText(message);
            
            return;
        }
        
        open(jspFO);
    }

    private FileObject findJspFile(){
        if(null == srcDoc || null == targetIdentifier){
            return null;
        }
        
        String relJspPath = targetIdentifier.startsWith("/") ? 
            targetIdentifier.substring(1) :
            targetIdentifier;
        
        FileObject jspFO = null;
        
        FileObject sourceFO = NbEditorUtilities.getFileObject(srcDoc);
        SourceGroup[] sgs = StripesPaletteUtilities.
                getSourceGroups(sourceFO, StripesPaletteUtilities.DOC_SOURCES);
        
        for(SourceGroup sg: sgs){
            FileObject rootFO = sg.getRootFolder();
            jspFO = rootFO.getFileObject(relJspPath);
            
            if(null != jspFO){
                break;
            }
        }
        
        
        
        
        // Here we're working out whether we're dealing with a relative link or not:
//        if (targetIdentifier.contains("/")){
//            String fullPath = sourceFO.getPath();
//            try {
//                URL f = new File(fullPath).toURI().resolve(targetIdentifier).toURL();
//                jspFO = URLMapper.findFileObject(f);
//            } catch (MalformedURLException ex) {
//                ex.printStackTrace();
//            }
//        } else {
//            jspFO = sourceFO.getParent().getFileObject(targetIdentifier);
//        }
        
        log.finest("For " + targetIdentifier + " found file " + jspFO);
        
        return jspFO;
    }
}
