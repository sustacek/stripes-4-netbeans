/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.config;

/**
 * Unchecked exception used by framework support configuration.
 * 
 * @author Josef Sustacek
 */
public class StripesConfigException extends RuntimeException {

    /**
     * Creates a new instance of <code>StripesConfigException</code> without detail message.
     */
    public StripesConfigException() {
    }

    /**
     * Constructs an instance of <code>StripesConfigException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public StripesConfigException(String msg) {
        super(msg);
    }
    
    public StripesConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
