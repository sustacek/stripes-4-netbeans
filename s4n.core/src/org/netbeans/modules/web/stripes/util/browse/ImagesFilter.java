/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.util.browse;

import java.io.File;
import java.io.FileFilter;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * This filter accepts all files representing images from given 
 * list of extensions.
 * 
 * @author Josef Sustacek
 */
public class ImagesFilter implements FileFilter {

    private String[] exts;
    private static final String[] DEFAULT_EXTS = 
            new String[] {"jpg", "jpeg", "gif", "png", "bmp"};

    /**
     * Constructs new filter based on given accepted file extensions.
     * 
     * If exts == null, default extensions are used 
     * ("jpg", "jpeg", "gif", "png", "bmp").
     */
    public ImagesFilter(String[] exts) {
        if(null != exts) {
            this.exts = exts;  
        } else {
            this.exts = DEFAULT_EXTS;
        }    
    }

    public boolean accept(File file) {
        if (file.isDirectory()) return false;
        
        FileObject fileFO = FileUtil.toFileObject(file);
        for(String ext: exts){
            if(ext.equals(fileFO.getExt())){
                return true;
            }
        }
        
        return false;
    }

}
