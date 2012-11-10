/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.web.stripes.wizards;

import java.awt.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;

public class ActionBeanWizardPanel implements WizardDescriptor.Panel<WizardDescriptor>
 {

    protected static final String EXTEND_ACTION_BEAN = "extend_action_bean";
    protected static final String ACTION_BEAN_CLASS = "action_bean_class";

    protected static final String OVERRIDE_CONTEXT_METHODS = "override_context_methods";
    protected static final String CONTEXT_CLASS = "context_class";

    protected static final String USE_URL_BINDING = "use_url_binding";
    protected static final String URL_BINDING = "url_binding";
    
    private WizardDescriptor wizard;

    public ActionBeanWizardPanel(WizardDescriptor wizard) {
        this.wizard = wizard;
    }
    
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private Component component;

    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new ActionBeanVisualPanel1(wizard, this);
        }
        return component;
    }

    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
    // If you have context help:
    // return new HelpCtx(SampleWizardPanel1.class);
    }

    // If it is always OK to press Next or Finish, then:
    public boolean isValid() {
        
        return ((ActionBeanVisualPanel1)getComponent()).isInputValid();
        
    // If it depends on some condition (form filled out...), then:
    // return someCondition();
    // and when this condition changes (last form field filled in...) then:
    // fireChangeEvent();
    // and uncomment the complicated stuff below.
    }

//    public final void addChangeListener(ChangeListener l) {
//    }
//
//    public final void removeChangeListener(ChangeListener l) {
//    }
//    
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0


    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    protected final void fireChangeEvent() {
        System.out.println("isValid:" + isValid());
        
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }


    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    public void readSettings(WizardDescriptor settings) {
    }

    public void storeSettings(WizardDescriptor settings) {
        WizardDescriptor wd = settings;
        ActionBeanVisualPanel1 configPanel = (ActionBeanVisualPanel1) getComponent();

        wd.putProperty(EXTEND_ACTION_BEAN, configPanel.extendExistingActionBean());
        wd.putProperty(ACTION_BEAN_CLASS, configPanel.getExtendedActionBeanClass());

        wd.putProperty(OVERRIDE_CONTEXT_METHODS, configPanel.overrideContextMethods());
        wd.putProperty(CONTEXT_CLASS, configPanel.getExtendedContextClass());

        wd.putProperty(USE_URL_BINDING, configPanel.useCustomUrlBinding());
        wd.putProperty(URL_BINDING, configPanel.getCustomUrlBinding());
    }
}

