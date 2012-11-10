/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.util;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.modules.web.stripes.wizards.ActionBeanVisualPanel1.AvailableClass;

/**
 *
 * @author Josef Sustacek
 */
public class Utils {
    /**
     * Returns given list with all items cast to desired class type.
     * 
     * @param <T> target class of items in resulting list
     * @param <S> source class of items in given list
     * @param source the list of items to be casted
     * 
     * @return list of appropriatelly casted items from the source list
     */
    @SuppressWarnings(value="unchecked")
    public static <T,S> List<T> castListItems(List<S> source){
        if(null == source){
            throw new IllegalArgumentException("Method argument is null.");
        }
        
        List<T> result = new ArrayList<T>(source.size());
        for(S item: source){
            result.add((T)item);    
        }
        
        return result;
    }
    
    /**
     * Parses given fully qualified name (com.my.package.MyClass) and returns 
     * apropriate object representing this FQN.
     * 
     * @param classFQN
     * @return
     */
    public static AvailableClass getClass(String classFQN){
        String pkg = "", clazz = "";
        int classDelim = classFQN.lastIndexOf(".");
        if(-1 != classDelim){
            pkg = classFQN.substring(0, classDelim);
            clazz = classFQN.substring(classDelim + 1, classFQN.length());
        } else {
            clazz = classFQN;
        }
        
        AvailableClass ac = new AvailableClass(pkg, clazz);
        
        return ac;
    }
}
