/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.modules.web.stripes.config;

import java.util.ArrayList;
import java.util.Locale;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Josef Sustacek
 */
public class ConfigurationValidatorTest {

    public ConfigurationValidatorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testIsValid() {
        final String VALID_FILTER = "*.jsp";
        final String INVALID_FILTER = "aaa";
        final String VALID_DISPATCHER = "*.action";
        final String INVALID_DISPATCHER = "/aaa/";
        
        StripesConfig valid1 = new StripesConfig(false, new ArrayList<Locale>(0), 
                Locale.CANADA, VALID_DISPATCHER, VALID_FILTER, false);
        
        assertTrue(ConfigurationValidator.isValid(valid1));
        
        StripesConfig invalid2 = new StripesConfig(false, new ArrayList<Locale>(0), 
                Locale.CANADA, INVALID_DISPATCHER, VALID_FILTER, false);
        
        assertFalse(ConfigurationValidator.isValid(invalid2));
        
        StripesConfig invalid3 = new StripesConfig(false, new ArrayList<Locale>(0), 
                Locale.CANADA, VALID_DISPATCHER, INVALID_FILTER, false);
        
        assertFalse(ConfigurationValidator.isValid(invalid3));
        
        StripesConfig invalid4 = new StripesConfig(false, new ArrayList<Locale>(0), 
                Locale.CANADA, INVALID_DISPATCHER, INVALID_FILTER, false);
        
        assertFalse(ConfigurationValidator.isValid(invalid4));
        
    }

    @Test
    public void testIsDispatcherUrlPatternValid() {
        assertFalse(ConfigurationValidator.isDispatcherUrlPatternValid(null));
        assertFalse(ConfigurationValidator.isDispatcherUrlPatternValid(""));
        assertFalse(ConfigurationValidator.isDispatcherUrlPatternValid("/"));
        assertFalse(ConfigurationValidator.isDispatcherUrlPatternValid("*."));
        assertFalse(ConfigurationValidator.isDispatcherUrlPatternValid("//"));
        assertFalse(ConfigurationValidator.isDispatcherUrlPatternValid("/aaa/"));
        assertFalse(ConfigurationValidator.isDispatcherUrlPatternValid("//*"));
        
        assertTrue(ConfigurationValidator.isDispatcherUrlPatternValid("*.action"));
        assertTrue(ConfigurationValidator.isDispatcherUrlPatternValid("*.doit4timestome"));
        assertTrue(ConfigurationValidator.isDispatcherUrlPatternValid("/anything/*"));
        assertTrue(ConfigurationValidator.isDispatcherUrlPatternValid("/doit4timestome/*"));
    }

    @Test
    public void testIsFilterUrlPatternValid() {
        assertFalse(ConfigurationValidator.isFilterUrlPatternValid(null));
        assertFalse(ConfigurationValidator.isFilterUrlPatternValid(""));
        assertFalse(ConfigurationValidator.isFilterUrlPatternValid("*."));
        assertFalse(ConfigurationValidator.isFilterUrlPatternValid(".rofl"));
        
        assertTrue(ConfigurationValidator.isFilterUrlPatternValid("*.jsp"));
        assertTrue(ConfigurationValidator.isFilterUrlPatternValid("*.ro4fl"));
    }

}