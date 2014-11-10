package com.epam.xmltransforming.listener;

import javax.servlet.ServletContextEvent;

/**
 * Application Lifecycle Listener implementation class ServletContextListener
 *
 */
public class ServletContextListener implements javax.servlet.ServletContextListener {

    /**
     * Default constructor. 
     */
    public ServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	System.setProperty("javax.xml.transform.TransformerFactory", 
    			"com.epam.xmltransforming.util.CachingTransformerFactory");
    	System.out.println("System property set to " + 
    			System.getProperty("javax.xml.transform.TransformerFactory"));
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }
	
}
