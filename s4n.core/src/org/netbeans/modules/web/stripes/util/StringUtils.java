/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.util;

/**
 *
 * @author Josef Sustacek
 */
public class StringUtils {
    
    /**
     * Returns true, if given string is null or empty.
     * 
     * @param s
     * @return
     */
    public static boolean nullOrEmpty(String s){
        return null == s || s.isEmpty();
    }
}
