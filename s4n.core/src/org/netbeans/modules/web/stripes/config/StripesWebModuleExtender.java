/*
 * Stripes1.5.java
 *
 * Created on October 23, 2008, 11:21 AM
 */
package org.netbeans.modules.web.stripes.config;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.web.stripes.util.Constants;
import org.netbeans.modules.web.api.webmodule.WebModule;
import org.netbeans.modules.web.spi.webmodule.WebModuleExtender;
import org.openide.filesystems.FileObject;
import org.openide.util.HelpCtx;

public class StripesWebModuleExtender extends WebModuleExtender {

    private StripesConfigPanel configPanel;
    private final StripesWebFrameworkProvider framework;

    private static final Logger LOG = Logger.getLogger(Constants.LOGGER_NAME);
    
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1);
    private StripesConfig initialSetup;
    
    public StripesWebModuleExtender(
            StripesWebFrameworkProvider framework, StripesConfig initialSetup) {
        this.framework = framework;
        this.initialSetup = initialSetup;
    }

    @Override
    public HelpCtx getHelp() {
        LOG.finest("getHelp called");
        return null;
    }
    
    @Override
    public boolean isValid() {
        StripesConfig config = framework.getConfiguration();
        boolean configValid = ConfigurationValidator.isValid(config);
        
        framework.checkErrors();
        
        if(!framework.getErrors().isEmpty()){
            
            framework.getController().setErrorMessage(
                framework.getErrors().toArray(new String[] {})[0]);
        }else{
            framework.getController().setErrorMessage(null);
        }
        
        LOG.finest("Is current config valid? " + configValid);
        return configValid;
    }

    @Override
    public StripesConfigPanel getComponent() {
        LOG.finest("getComponent called");
        
        if (configPanel == null) {
            configPanel = new StripesConfigPanel(this, initialSetup);
            
            LOG.finest("new component created");
        }
        
        return configPanel;
    }

    @Override
    public Set<FileObject> extend(WebModule wm) {
        return framework.extendImpl(wm);
    }
    
    @Override
    public void update() {
        LOG.finest("update called");
        
        framework.checkErrors();
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        LOG.finest("listener added");
        
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        LOG.finest("listener removed");
        
        listeners.remove(listener);
    }

    /**
     * Notifies all registered listeners, that given event occured.
     * 
     * @param event
     */
    public void notifyListeners(ChangeEvent event){
        for(ChangeListener l : listeners){
            l.stateChanged(event);
        }
    }
}
