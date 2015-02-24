package com.epam.testapp.database.connection;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.epam.testapp.exception.DaoException;

/**
 * Connection pool realization. It is using two blocking queues for storing
 * opened connections: freeConnections for connections that can be taken by user
 * and usedConncetions for connections that are using right now.
 */
public final class ConnectionPool {

	// Exception messages

	private static final String EXC_DRIVER_NOT_FOUND = "Driver not found";
	private static final String EXC_INT = "Interrupted";
	private static final String EXC_CREATE = "Can't create connection";
	private static final String EXC_CLOSE = "Cannot close connection";

	// Database propetries

	private static final String USERNAME = "user";
	private static final String PASSWORD = "password";
	private static final String CHARACTER_ENCODING = "characterEncoding";
	private static final String USE_UNICODE = "useUnicode";

	// Collections containing opened connections

	private BlockingQueue<Connection> freeConnections;
	private BlockingQueue<Connection> usedConnections;

	private static ConnectionPool instance;

	/**
	 * Method for getting instance of ConnectionPool.
	 * 
	 * @return current instance of ConnectionPool
	 * @throws DaoException
	 */
	public static ConnectionPool getInstance() throws DaoException {
		return instance;
	}

	/**
	 * Public constructor for creating pool of connections.
	 * 
	 * @param username
	 * @param password
	 * @param url
	 *            - url of database.
	 * @param capacity
	 *            - number of opened connections.
	 * @param driver
	 *            - driver name used for connection with database.
	 * @param encoding
	 *            - encoding of database.
	 * @param useUnicode
	 *            - if true, forces database to use Unicode
	 * @throws DaoException
	 *             if given driver not found or having problems with database
	 *             access
	 */
	public ConnectionPool(String username, String password, String url,
			int capacity, String driver, String encoding, String useUnicode)
			throws DaoException {

		// Creating Properties to create connections
		Properties properties = new Properties();
		properties.put(USERNAME, username);
		properties.put(PASSWORD, password);
		properties.put(CHARACTER_ENCODING, encoding);
		properties.put(USE_UNICODE, useUnicode);
		try {
			// Loading driver
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// If driver not found
			throw new DaoException(EXC_DRIVER_NOT_FOUND, e);
		}

		// Creating queues
		freeConnections = new LinkedBlockingQueue<Connection>(capacity);
		usedConnections = new LinkedBlockingQueue<Connection>(capacity);

		for (int i = 0; i < capacity; i++) {
			try {
				// Filling queue with opened connections
				Connection connection = DriverManager.getConnection(url,
						properties);
				freeConnections.add(connection);
			} catch (IllegalStateException e) {
				// If queue is full
				throw new DaoException(EXC_INT, e);
			} catch (SQLException e) {
				// If a database access error occurs
				throw new DaoException(EXC_CREATE, e);
			}
		}
	}

	/**
	 * Gets opened connection from pool. If pool is empty, thread is blocked
	 * while some connection will be free.
	 * 
	 * @return Opened connection.
	 * @throws DaoException
	 *             if thread is interrupted
	 */
	public Connection getConnection() throws DaoException {

		Connection connection = null;
		try {
			connection = freeConnections.take();

			// Put taken connection in queue of currently using connections
			usedConnections.add(connection);
		} catch (InterruptedException e) {
			throw new DaoException(EXC_INT, e);
		}

		return connection;
	}

	/**
	 * Returns connection back to the pool
	 * 
	 * @param connection
	 *            - current connection
	 * @throws DaoException
	 *             if thread is interrupted.
	 */
	public void freeConnection(Connection connection) throws DaoException {

			// Check for existence
			if (connection != null) {
				usedConnections.remove(connection);
				freeConnections.add(connection);
			}
	}

	/**
	 * Gets count of free connections.
	 * 
	 * @return count of free connections.
	 */
	public int getFreeConnectionsCount() {
		return freeConnections.size();
	}

	/**
	 * Closes all connections.
	 * 
	 * @throws DaoException
	 *             if thread is interrupted
	 */
	public void closeConnections() throws DaoException {

		try {
			while (!freeConnections.isEmpty()) {
				freeConnections.poll().close();
			}
			while (!usedConnections.isEmpty()) {
				usedConnections.poll().close();
			}
		} catch (SQLException e) {
			throw new DaoException(EXC_CLOSE, e);
		}
	}
}
