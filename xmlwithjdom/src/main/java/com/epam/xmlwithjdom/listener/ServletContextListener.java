package com.epam.xmlwithjdom.listener;

import javax.servlet.ServletContextEvent;

/**
 * Application Lifecycle Listener implementation class ServletContextListener.
 * It is used for changing system property to use custom TransformerFactory class
 *
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {
	private static final String TRANSFORM_FACTORY_INTERFACE = "javax.xml.transform.TransformerFactory";
	private static final String TRANSFORM_FACTORY_CLASS = "com.epam.xmlwithjdom.util.CachingTransformerFactory";
	
    /**
     * Default constructor. 
     */
    public ServletContextListener() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	System.setProperty(TRANSFORM_FACTORY_INTERFACE, TRANSFORM_FACTORY_CLASS);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    }
	
}
