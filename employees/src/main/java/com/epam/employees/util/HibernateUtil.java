package com.epam.employees.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Util class for creating session factory and getting access to it.
 * 
 * @author Aliaksandr_Neuhen
 *
 */
public final class HibernateUtil {
	private static volatile SessionFactory sessionFactory = null;

	/**
	 * Creates sessionFactory
	 */
	private static void createSessionFactory() {
		// Create Hibernate configuration object
		Configuration config = new Configuration();
		// Parse hibernate.cfg.xml and get properties and mappings from it
		config.configure();

		Properties properties = config.getProperties();
		// Create a default registry builder
		StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
		// Apply settings for registry builder
		registryBuilder.applySettings(properties);
		// Build standard service registry
		ServiceRegistry serviceRegistry = registryBuilder.build();
		// Build session factory using the properties and mappings in configuration
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			synchronized (HibernateUtil.class) {
				if (sessionFactory == null) {
					createSessionFactory();
				}
			}
		}
		return sessionFactory;
	}
}
