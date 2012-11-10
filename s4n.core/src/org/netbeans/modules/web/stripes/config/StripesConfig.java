/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class encapsulates the configuration for Stripes Framework that is 
 * available when creating new Web application project.
 * 
 * @author Josef Sustacek
 */
public class StripesConfig {
    private boolean bundleCreated;

    private List<Locale> includedLocalizations;
    private Locale defaultLocalization;
    
    private String stripesDispatcherUrlPattern;
    private String stripesFilterUrlPattern;

    private boolean jstlIncluded;

    static final String EN_LOCALIZATION_LABEL = "English (en)";
    static final String CS_LOCALIZATION_LABEL = "Czech (cs)";
    
    
    public StripesConfig() {
    }

    public StripesConfig(boolean bundleCreated, List<Locale> includedLocalizations, 
            Locale defaultLocalization, String stripesDispatcherUrlPattern, 
            String stripesFilterUrlPattern, boolean jstlIncluded) {
        this.bundleCreated = bundleCreated;
        this.includedLocalizations = includedLocalizations;
        this.defaultLocalization = defaultLocalization;
        this.stripesDispatcherUrlPattern = stripesDispatcherUrlPattern;
        this.stripesFilterUrlPattern = stripesFilterUrlPattern;
        this.jstlIncluded = jstlIncluded;
    }

    private static final List<Locale> suportedLocales = 
            new ArrayList<Locale>(2);
    
    static {
        suportedLocales.add(Locale.ENGLISH);
        suportedLocales.add(new Locale("cs"));
    }
    
    /**
     * Returns the initial configuration that should be as default in UI.
     * 
     * @return
     */
    public static StripesConfig getInitialConfig(){
        StripesConfig configuration = new StripesConfig(
                true, 
                suportedLocales, 
                Locale.ENGLISH, 
                "*.action", 
                "*.jsp", 
                true);
        
        return configuration;
    }
    
    
    /**
     * Default localization, specifies the language of StripesResources.properties 
     * file.
     */
    public Locale getDefaultLocalization() {
        return defaultLocalization;
    }

    public void setDefaultLocalization(Locale defaultLocalization) {
        this.defaultLocalization = defaultLocalization;
    }

    /**
     * List of localizations, each means that a 
     * "StripesResources_&lt;locale&gt;.properties will be additionally cteated 
     * in default package.
     */
    public List<Locale> getIncludedLocalizations() {
        return includedLocalizations;
    }

    public void setIncludedLocalizations(List<Locale> includedLocalizations) {
        this.includedLocalizations = includedLocalizations;
    }

    /**
     * Should StripesResources.properties file be created in default package?
     */
    public boolean isBundleCreated() {
        return bundleCreated;
    }

    public void setBundleCreated(boolean bundleCreated) {
        this.bundleCreated = bundleCreated;
    }

    /**
     * Returns true, if JSTL library should be included in project's libraries.
     * 
     * @return
     */
    public boolean isJstlIncluded() {
        return jstlIncluded;
    }

    public void setJstlIncluded(boolean jstlIncluded) {
        this.jstlIncluded = jstlIncluded;
    }

    /**
     * Url mapping used in web.xml file for Stripes Dispatcher servlet. 
     * Typically "*.action" or "/action/*".
     * 
     * @return
     */
    public String getStripesDispatcherUrlPattern() {
        return stripesDispatcherUrlPattern;
    }

    public void setStripesDispatcherUrlPattern(String stripesDispatcherUrlPattern) {
        this.stripesDispatcherUrlPattern = stripesDispatcherUrlPattern;
    }

    /**
     * Url mapping used in web.xml file for Stripes Filter. 
     * Typically "*.jsp" as all jsp file requests should be "enriched" by 
     * the Stripes information.
     * 
     * @return
     */
    public String getStripesFilterUrlPattern() {
        return stripesFilterUrlPattern;
    }

    public void setStripesFilterUrlPattern(String stripesFilterUrlPattern) {
        this.stripesFilterUrlPattern = stripesFilterUrlPattern;
    }
}
