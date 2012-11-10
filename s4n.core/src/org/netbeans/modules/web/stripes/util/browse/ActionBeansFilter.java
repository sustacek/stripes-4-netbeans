/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.util.browse;

import java.io.File;
import java.io.FileFilter;
import org.netbeans.api.java.source.JavaSource;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * This filter accepts all files representing Stripes Action Bean class.
 * @author Josef Sustacek
 */
public class ActionBeansFilter implements FileFilter {

    // TODO
    @Override
    public boolean accept(File file) {
        FileObject fileFO = FileUtil.toFileObject(file);
        JavaSource javaSource = JavaSource.forFileObject(fileFO);
        
        
        return file.isFile() &&
                "java".equals(fileFO.getExt()) &&
                true;
    }

}
