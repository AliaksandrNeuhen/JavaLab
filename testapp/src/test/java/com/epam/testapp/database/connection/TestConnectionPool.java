package com.epam.testapp.database.connection;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.epam.testapp.exception.DaoException;

public class TestConnectionPool {
	private ConnectionPool cp = null;
	private static final String USERNAME = "testadmin";
	private static final String PASSWORD = "testroot";
	private static final String URL = "jdbc:oracle:thin:@//localhost:1521/xe";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String ENCODING = "UTF-8";
	private static final String USE_UNICODE = "true";
	private static final int CAPACITY = 10;
	
	@Before
	public void init(){
		try {
			cp = new ConnectionPool(USERNAME, PASSWORD, URL, CAPACITY, DRIVER, ENCODING, USE_UNICODE);
		} catch (DaoException e) {
			fail("Can't create connection pool");
		}
	}
	
	@After
	public void destroy(){
		try {
			cp.closeConnections();
		} catch (DaoException e) {
			fail("Can't close connections");
		}
	}
	
	@Test
	public void testGetConnection(){
		Connection conn = null;
		try {
			conn = cp.getConnection();
			
			assertEquals("freeConnections count must be == CAPACITY - 1",CAPACITY - 1,
					cp.getFreeConnectionsCount());
			
			if (conn == null){
				fail("Connection is empty");
			}
			
		} catch (DaoException e) {
			fail("Exception has been thrown");
			
		} finally {
			try {
				cp.freeConnection(conn);
			} catch (DaoException e) {
				fail("Can't free connection");
			}
		}
	}

	@Test
	public void testFreeConnection(){
		Connection conn = null;
		try {
			conn = cp.getConnection();
		} catch (DaoException e) {
			fail("Failed to create connection");
		}
		try {
			cp.freeConnection(conn);
		} catch (DaoException e){
			fail("Failed to free connection");
		}
		assertEquals("freeConnections count must be == CAPACITY",CAPACITY,
				cp.getFreeConnectionsCount());
	}
	
	@Test
	public void testCloseConnections(){
		try {
			cp.closeConnections();
			assertEquals("freeConnections must be empty", 0, cp.getFreeConnectionsCount());
		} catch (DaoException e) {
			fail("Can't close connections");
		}
	}
}
