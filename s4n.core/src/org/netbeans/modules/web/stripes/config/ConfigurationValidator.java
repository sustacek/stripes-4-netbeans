/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.config;

import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.netbeans.modules.web.stripes.util.StringUtils;

/**
 *
 * @author Josef Sustacek
 */
public class ConfigurationValidator {
    
    private static final Logger LOG = 
            Logger.getLogger(ConfigurationValidator.class.getName());
    
    public static boolean isValid(StripesConfig config){
        if(null == config){
            throw new IllegalArgumentException("Configuration is empty.");
        }
        
        return isDispatcherUrlPatternValid(config.getStripesDispatcherUrlPattern()) && 
                isFilterUrlPatternValid(config.getStripesFilterUrlPattern());
    }
    
    private static final Pattern filterPattern = Pattern.compile("\\*\\.\\w+");
    private static final Pattern dispatcherPattern = 
            Pattern.compile("\\*\\.\\w+|/\\w+/\\*");
    
    public static boolean isDispatcherUrlPatternValid(String urlPattern){
        boolean valid = !StringUtils.nullOrEmpty(urlPattern) && 
                dispatcherPattern.matcher(urlPattern).matches();
        
//        LOG.info("Is dispatcher valid? " + urlPattern + " -> " + valid);
        
        return valid;
    } 
    
    public static boolean isFilterUrlPatternValid(String urlPattern){
        boolean valid = !StringUtils.nullOrEmpty(urlPattern) && 
                filterPattern.matcher(urlPattern).matches();
                
//        LOG.info("Is filter valid? " + urlPattern + " -> " + valid);
        
        return valid;
    } 
}
