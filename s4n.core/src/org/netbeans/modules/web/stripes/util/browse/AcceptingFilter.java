/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.util.browse;

import java.io.File;
import java.io.FileFilter;

/**
 * This filter accepts all files.
 * 
 * @author Josef Sustacek
 */
public class AcceptingFilter implements FileFilter {

    public boolean accept(File pathname) {
        return true;
    }

}
