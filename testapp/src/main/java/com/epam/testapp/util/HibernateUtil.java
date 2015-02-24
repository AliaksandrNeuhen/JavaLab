package com.epam.testapp.util;

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
	private static SessionFactory sessionFactory = null;

	/**
	 * Creates sessionFactory
	 */
	private static void createSessionFactory() {
		Configuration config = new Configuration();
		config.configure();
		Properties properties = config.getProperties();
		StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
		registryBuilder.applySettings(properties);
		ServiceRegistry serviceRegistry = registryBuilder.build();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
	}

	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			createSessionFactory();
		}
		return sessionFactory;
	}
}
