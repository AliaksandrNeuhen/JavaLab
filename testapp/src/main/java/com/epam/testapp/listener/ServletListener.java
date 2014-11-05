package com.epam.testapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.beanutils.ConvertUtils;

import com.epam.testapp.util.DateConverter;

/**
 * Application Lifecycle Listener implementation class ServletListener
 *
 */
public class ServletListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public ServletListener() {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		DateConverter dateConverter = new DateConverter();
		ConvertUtils.register(dateConverter, java.util.Date.class);
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
	}

}
