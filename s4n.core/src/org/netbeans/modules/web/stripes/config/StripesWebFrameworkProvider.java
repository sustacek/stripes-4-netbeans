/*
 * Stripes1.5.java
 *
 * Created on October 23, 8, 11:21 AM
 */
package org.netbeans.modules.web.stripes.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.modules.web.stripes.util.Constants;
import org.netbeans.api.java.classpath.ClassPath;
import org.netbeans.api.java.project.JavaProjectConstants;
import org.netbeans.api.java.project.classpath.ProjectClassPathModifier;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.api.project.libraries.Library;
import org.netbeans.api.project.libraries.LibraryManager;

import org.netbeans.modules.j2ee.dd.api.common.CommonDDBean;
import org.netbeans.modules.j2ee.dd.api.common.CreateCapability;
import org.netbeans.modules.j2ee.dd.api.common.InitParam;
import org.netbeans.modules.j2ee.dd.api.common.VersionNotSupportedException;
import org.netbeans.modules.j2ee.dd.api.web.DDProvider;
import org.netbeans.modules.j2ee.dd.api.web.Filter;
import org.netbeans.modules.j2ee.dd.api.web.FilterMapping;
import org.netbeans.modules.j2ee.dd.api.web.Servlet;
import org.netbeans.modules.j2ee.dd.api.web.ServletMapping;

import org.netbeans.modules.j2ee.dd.api.web.WebApp;

import org.netbeans.modules.j2ee.dd.api.web.WelcomeFileList;
import org.netbeans.modules.web.api.webmodule.ExtenderController;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.netbeans.modules.web.spi.webmodule.WebFrameworkProvider;
import org.netbeans.modules.web.spi.webmodule.WebModuleExtender;
import org.netbeans.modules.web.stripes.palette.StripesPaletteUtilities;
import org.openide.ErrorManager;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.util.Exceptions;

public class StripesWebFrameworkProvider extends WebFrameworkProvider {

    private static final Logger log = Logger.getLogger(Constants.LOGGER_NAME);
    private StripesWebModuleExtender extender;
    private ExtenderController controller;
    
    private static final String FRAMEWORK_NAME = "Stripes 1.5";
    private static final String FRAMEWORK_DESCRIPTION = "Modern, annotation " +
            "driven and configuration-files-less MVC framework.";
    
    private static final String JSTL_LIB_NAME = "JSTL 1.2";
    private static final String STRIPES_LIB_NAME = "Stripes 1.5";
    
    private static final String DISPATCHER_ERROR_MSG = "Dispatcher url mapping has wrong format.";
    private static final String FILTER_ERROR_MSG = "Filter url mapping has wrong format.";
    
    private static final String DISPATCHER_SERVLET_CLASS = "net.sourceforge.stripes.controller.DispatcherServlet";
    private static final String DISPATCHER_SERVLET_NAME = "DispatcherServlet";
    private static final String FILTER_CLASS = "net.sourceforge.stripes.controller.StripesFilter";
    private static final String FILTER_NAME = "StripesFilter";
    private static final String ENCODING = "utf-8";

    private final Set<String> errors = new HashSet<String>(2);
    
    public StripesWebFrameworkProvider() {
        super(FRAMEWORK_NAME, FRAMEWORK_DESCRIPTION);
    }

    @Override
    public boolean isInWebModule(WebModule wm) {
        return true;
    }

    @Override
    public WebModuleExtender createWebModuleExtender(WebModule wm,
                                                     ExtenderController controller) {
        extender = new StripesWebModuleExtender(this,
                StripesConfig.getInitialConfig());
        this.controller = controller;

        return extender;
    }

    /**
     * No configuration files for Stripes.
     * 
     * @param wm
     * @return
     */
    @Override
    public File[] getConfigurationFiles(WebModule wm) {
        return null;
    }

    @Override
    public String getDescription() {
        return FRAMEWORK_DESCRIPTION;
    }

    @Override
    public String getName() {
        return FRAMEWORK_NAME;
    }

    /**
     * Implementation of extend method from StripesWebModuleExtender.
     * 
     * @param wm
     * @return
     */
    protected Set<FileObject> extendImpl(WebModule wm) {
        EnableFrameworkAction enableFrameworkAction =
                new EnableFrameworkAction(wm, extender.getComponent());
        FileObject webInf = wm.getWebInf();
        if (webInf != null) {
            try {
                FileSystem fs = webInf.getFileSystem();
                fs.runAtomicAction(enableFrameworkAction);
            } catch (IOException e) {
                ErrorManager.getDefault().notify(e);
                return null;
            }
        }
        return enableFrameworkAction.getFilesToOpen();
    }

    /**
     * Checks the errors based on current cunfiguration in UI.
     * 
     * Clears all errors, calls getComponent(), gets the corresponding 
     * configuration and if any error occurs (badly formed url mapping etc.), 
     * adds and error message to errors.
     */
    protected void checkErrors() {
        errors.clear();
        StripesConfig conf = getConfiguration();

        String newDispatcherValue = conf.getStripesDispatcherUrlPattern();

        if (!ConfigurationValidator.isDispatcherUrlPatternValid(newDispatcherValue)) {
            errors.add(DISPATCHER_ERROR_MSG);
        } else {
            errors.remove(DISPATCHER_ERROR_MSG);
        }

        String newFilterValue = conf.getStripesFilterUrlPattern();

        if (!ConfigurationValidator.isFilterUrlPatternValid(newFilterValue)) {
            errors.add(FILTER_ERROR_MSG);
        } else {
            errors.remove(FILTER_ERROR_MSG);
        }
    }

    public Set<String> getErrors() {
        return errors;
    }

    /**
     * Gets the StripesConfig object filled with values distilled from
     * UI.
     * 
     * @return
     */
    protected StripesConfig getConfiguration() {
        StripesConfig config = new StripesConfig();
        StripesConfigPanel panel = extender.getComponent();

        // main tab
        config.setStripesDispatcherUrlPattern(panel.getDispatcherUrl());
        config.setStripesFilterUrlPattern(panel.getFilterUrl());

        // localization
        config.setBundleCreated(panel.getCreateResourcesFile());

        if (config.isBundleCreated()) {
            // included localizations
            config.setIncludedLocalizations(panel.getIncludedLocalizations());

            // default localization
            config.setDefaultLocalization(panel.getDefaultLocalization());
        }

        // libraries
        config.setJstlIncluded(panel.getIncludeJSTL());

        return config;
    }
    
    protected ExtenderController getController() {
        return controller;
    }

    private static class EnableFrameworkAction implements FileSystem.AtomicAction {

        private Set<FileObject> filesToOpen = new LinkedHashSet<FileObject>();
        private WebModule webModule;
        private StripesConfigPanel frameworkPanel;

        public EnableFrameworkAction(WebModule webModule, StripesConfigPanel frameworkPanel) {
            this.webModule = webModule;
            this.frameworkPanel = frameworkPanel;
        }

        private static enum FilterMappingType {
            URL_PATTERN,
            SERVLET_NAME
        }

        private static enum FilterMappingDispatcher {
            REQUEST
        }

        public void run() throws IOException {
            // MODIFY WEB.XML
            FileObject dd = webModule.getDeploymentDescriptor();
            WebApp webApp = DDProvider.getDefault().getDDRoot(dd);
            Project project = FileOwnerQuery.getOwner(dd);


            addServlet(webApp, DISPATCHER_SERVLET_NAME,
                    DISPATCHER_SERVLET_CLASS, frameworkPanel.getDispatcherUrl(), "1");
            
            Map<String, String> initParams = new HashMap<String, String>(4);
            initParams.put("ActionResolver.Packages", "TODO -- enter comma " +
                    "separated packages with Action Beans here");
            initParams.put("LocalePicker.Locales", getLocalePickerContent(
                    frameworkPanel.getDefaultLocalization(),
                    frameworkPanel.getIncludedLocalizations()));
//            initParams.put("Interceptor.Classes", "net.sourceforge.stripes.controller.BeforeAfterMethodInterceptor");
//            initParams.put("MultipartWrapper.Class", "net.sourceforge.stripes.controller.multipart.CommonsMultipartWrapper");
            addFilter(webApp, "StripesFilter", FILTER_CLASS, frameworkPanel.getFilterUrl(), 
                    initParams, FilterMappingDispatcher.REQUEST);

            addFilterMapping(webApp, FILTER_NAME, DISPATCHER_SERVLET_NAME, 
                    FilterMappingType.SERVLET_NAME, FilterMappingDispatcher.REQUEST);


           WelcomeFileList welcomeFiles = webApp.getSingleWelcomeFileList();
            if (welcomeFiles == null) {
                try {
                    welcomeFiles = (WelcomeFileList) webApp.createBean("WelcomeFileList");
                    webApp.setWelcomeFileList(welcomeFiles);
                } catch (ClassNotFoundException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
            if (welcomeFiles.sizeWelcomeFile() == 0) {
                welcomeFiles.addWelcomeFile("index.jsp");
            }

            webApp.write(dd);
            log.info("web.xml modified");

            addFileToOpen(dd);

            // ADD libraries
            addLibraryToWebModule(webModule, STRIPES_LIB_NAME);
            log.info("stripes library added");
            if (frameworkPanel.getIncludeJSTL()) {
                addLibraryToWebModule(webModule, JSTL_LIB_NAME);
                log.info("jstl library added");
            }


            // CREATE FOLDERS FOR FILES
            FileObject webInf = webModule.getWebInf();
            FileObject jspf = webInf.createFolder("jspf");
            FileObject jsp = webInf.createFolder("jsp");
            FileObject layout = webInf.createFolder("layout");
            
            FileObject docBase = webModule.getDocumentBase();
            FileObject css = docBase.createFolder("css");
            FileObject js = docBase.createFolder("js");

            FileObject resourcesRoot = getProjectResourcesRoot(project);


            // COPY TEMPLATE STRIPES RESOURCES (BUNDLES, LAYOUTS, CSS, JS...)
            if(frameworkPanel.getCreateResourcesFile()){
                Locale defaultLang = frameworkPanel.getDefaultLocalization();
                
                copyResource("StripesResources_" + defaultLang + ".properties", 
                        FileUtil.createData(resourcesRoot, "StripesResources.properties"));
                
                log.info("default resource bundle created");
                
                for(Locale locale: frameworkPanel.getIncludedLocalizations()){
                    copyResource("StripesResources_" + locale + ".properties", 
                        FileUtil.createData(resourcesRoot, "StripesResources_" + locale + ".properties"));
                    
                    log.info(locale + " resource bundle created");
                } 
                
            }
     
            copyResource("menu.jspf", FileUtil.createData(jspf, "menu.jspf"));
            copyResource("footer.jspf", FileUtil.createData(jspf, "footer.jspf"));
            copyResource("layout-definition.jsp", FileUtil.createData(layout, "layout-definition.jsp"));
            copyResource("layout-usage.jsp", FileUtil.createData(jsp, "layout-usage.jsp"));
            copyResource("javascript.js", FileUtil.createData(js, "javascript.js"));
            copyResource("style.css", FileUtil.createData(css, "style.css"));
            
            log.info("jsp resources copied");
            
            // MODIFY EXISTING INDEX.JSP
//            FileObject documentBase = webModule.getDocumentBase();
//            FileObject indexJsp = documentBase.getFileObject("index.jsp");
//            if (indexJsp == null) {
//                indexJsp = FileUtil.createData(documentBase, "index.jsp");
//            }
//            addFileToOpen(copyResource("redirect.jsp", indexJsp));
        }

        public void addFileToOpen(FileObject file) {
            filesToOpen.add(file);
        }

        public Set<FileObject> getFilesToOpen() {
            return filesToOpen;
        }

        protected FileObject copyResource(String resourceName, FileObject target) throws UnsupportedEncodingException, IOException {
            InputStream in = getClass().getResourceAsStream("resources/" + resourceName);
            String lineSeparator = System.getProperty("line.separator");
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, ENCODING));
            try {
                String line = reader.readLine();
                while (line != null) {
                    buffer.append(line);
                    buffer.append(lineSeparator);
                    line = reader.readLine();
                }
            } finally {
                reader.close();
            }
            FileLock lock = target.lock();
            try {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(target.getOutputStream(lock), ENCODING));
                try {
                    writer.write(buffer.toString());
                } finally {
                    writer.close();
                }
            } finally {
                lock.releaseLock();
            }
            return target;
        }

        private void addLibraryToWebModule(WebModule wm, String libraryName) {
            FileObject docroot = wm.getDocumentBase();
            FileObject srcRootFO = null;

            Project project = FileOwnerQuery.getOwner(docroot);
            SourceGroup[] sgs = StripesPaletteUtilities.getSourceGroups(project,
                    JavaProjectConstants.SOURCES_TYPE_JAVA);
            if(0 != sgs.length){
                srcRootFO = sgs[0].getRootFolder();
            }


            Library lib = LibraryManager.getDefault().getLibrary(libraryName);
            if (lib != null && srcRootFO != null) {
                try {
                    ProjectClassPathModifier.addLibraries(
                            new Library[]{lib}, srcRootFO, ClassPath.COMPILE);
                } catch (IOException ioe) {
                    Exceptions.printStackTrace(ioe);
                    Logger.getLogger("global").log(Level.INFO,
                            "ProjectClassPathModifier could not add " +
                            "the " + libraryName + " library to project's classpath.");
                }
            }

        }

        protected Servlet addServlet(WebApp webApp, String name,
                                     String classname, String pattern, String loadOnStartup) throws IOException {
            Servlet servlet = (Servlet) createBean(webApp, "Servlet");
            servlet.setServletName(name);
            servlet.setServletClass(classname);
            if (loadOnStartup != null) {
                servlet.setLoadOnStartup(new BigInteger(loadOnStartup));
            }
            webApp.addServlet(servlet);
            if (pattern != null) {
                addServletMapping(webApp, name, pattern);
            }
            return servlet;
        }

        protected ServletMapping addServletMapping(WebApp webApp, String name, String pattern) throws IOException {
            ServletMapping mapping = (ServletMapping) createBean(webApp, "ServletMapping");
            mapping.setServletName(name);
            mapping.setUrlPattern(pattern);
            webApp.addServletMapping(mapping);
            
            return mapping;
        }

        protected Filter addFilter(WebApp webApp, String name, String classname, 
                String pattern, Map<String, String> initParams,
                FilterMappingDispatcher mappingDispatcher) throws IOException {
            Filter filter = (Filter) createBean(webApp, "Filter");
            filter.setFilterName(name);
            filter.setFilterClass(classname);
            
            if(null != initParams){
                for(String paramName: initParams.keySet()){
                    InitParam ip = (InitParam) createBean(webApp, "InitParam");
                    ip.setParamName(paramName);
                    ip.setParamValue(initParams.get(paramName));
                    
                    filter.addInitParam(ip);
                }
            }
            
            webApp.addFilter(filter);
            if (pattern != null) {
                addFilterMapping(webApp, name, pattern, 
                        FilterMappingType.URL_PATTERN, mappingDispatcher);
            }
            return filter;
        }

        protected FilterMapping addFilterMapping(WebApp webApp, String filterName,
                String mappingValue, FilterMappingType mappingType, 
                FilterMappingDispatcher mappingDispatcher) throws IOException {

            FilterMapping mapping = (FilterMapping) createBean(webApp, "FilterMapping");
            mapping.setFilterName(filterName);
            switch(mappingType){
                case SERVLET_NAME:
                    mapping.setServletName(mappingValue);
                    break;
                case URL_PATTERN:
                    mapping.setUrlPattern(mappingValue);
                    break;
                default:
                    throw new IllegalArgumentException("Not supported filter " +
                            "mapping type");
            }

            try {
                mapping.setDispatcher(new String[] {mappingDispatcher.toString()});
            } catch (VersionNotSupportedException ve){
                log.warning(ve.getMessage());
            }

            webApp.addFilterMapping(mapping);
            
            return mapping;
        }

        protected InitParam addContextParam(WebApp webApp, String name, String value) throws IOException {
            InitParam initParam = (InitParam) createBean(webApp, "InitParam");
            initParam.setParamName(name);
            initParam.setParamValue(value);
            webApp.addContextParam(initParam);
            return initParam;
        }

        protected CommonDDBean createBean(CreateCapability creator, String beanName) throws IOException {
            CommonDDBean bean = null;
            try {
                bean = creator.createBean(beanName);
            } catch (ClassNotFoundException ex) {
                ErrorManager.getDefault().notify(ErrorManager.EXCEPTION, ex);
                throw new IOException("Error creating bean with name:" + beanName);
            }
            return bean;
        }

        protected Library getLibrary(String name) {
            return LibraryManager.getDefault().getLibrary(name);
        }

        protected FileSystem getDefaultFileSystem() {
            return Repository.getDefault().getDefaultFileSystem();
        }

        private String getLocaleId(String localeLabel){
            if(StripesConfig.CS_LOCALIZATION_LABEL.equals(localeLabel)){
                return "cs";
            }else if(StripesConfig.EN_LOCALIZATION_LABEL.equals(localeLabel)){
                return "en";
            } else{
                throw new RuntimeException(localeLabel + " is not supported locale label.");
            }
        }

        private String getLocalePickerContent(Locale defaultLocalization, 
                List<Locale> includedLocalizations) {
            StringBuilder sb = new StringBuilder();
            sb.append(defaultLocalization + ":" + ENCODING + ",");
            
            for(Locale locale: includedLocalizations){
                if(!locale.equals(defaultLocalization)){
                    sb.append(locale + ":" + ENCODING + ",");
                }
            }
            
            sb.replace(sb.length() - 1, sb.length(), "");
            
//            "cs:utf-8,en:utf-8"
            return sb.toString();
        }
        
        private FileObject getProjectJavaSourcesRoot(Project project) {
            Sources srcs = ProjectUtils.getSources(project);
            SourceGroup[] javaSrcs = 
                    srcs.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_JAVA);
            if(javaSrcs.length > 0){
                return javaSrcs[0].getRootFolder();
            }else {
                throw new RuntimeException("No java sources in given project.");
            }
        }

        private FileObject getProjectResourcesRoot(Project project) {
            Sources srcs = ProjectUtils.getSources(project);
            SourceGroup[] resources = 
                    srcs.getSourceGroups(JavaProjectConstants.SOURCES_TYPE_RESOURCES);
            if(resources.length > 0){
                return resources[0].getRootFolder();
            }else {
                // no resources in given project => place together with java sources
                return getProjectJavaSourcesRoot(project);
//                throw new RuntimeException("No resources in given project.");
            }
        }
    }
}