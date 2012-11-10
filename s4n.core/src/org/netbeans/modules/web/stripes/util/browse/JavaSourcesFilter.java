/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.util.browse;

import java.io.File;
import java.io.FileFilter;
import org.openide.filesystems.FileUtil;

/**
 * This filter accepts all files representing Java source files.
 * 
 * @author Josef Sustacek
 */
public class JavaSourcesFilter implements FileFilter{

    public boolean accept(File file) {
        return file.isFile() &&
            FileUtil.toFileObject(file).getPath().endsWith(".java");
    }

}
