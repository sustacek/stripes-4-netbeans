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

public class LayoutDefinitionWizardPanel implements WizardDescriptor.Panel<WizardDescriptor> {

    protected static final String LAYOUT_DEFINITION_NAME = "layout_name";
    
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private Component component;
    private WizardDescriptor wizard;

    public LayoutDefinitionWizardPanel(WizardDescriptor wizard) {
        this.wizard = wizard;
    }
    
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent() {
        if (component == null) {
            component = new LayoutDefinitionVisualPanel1(this, wizard);
        }
        return component;
    }

    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
    // If you have context help:
    // return new HelpCtx(SampleWizardPanel1.class);
    }

    public boolean isValid() {
        return ((LayoutDefinitionVisualPanel1)getComponent()).isInputValid();
    }

//    public final void addChangeListener(ChangeListener l) {
//    }
//
//    public final void removeChangeListener(ChangeListener l) {
//    }
    
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
        LayoutDefinitionVisualPanel1 configPanel = (LayoutDefinitionVisualPanel1) getComponent();

        settings.putProperty(LAYOUT_DEFINITION_NAME, configPanel.getDefinitionName());
    }
}

