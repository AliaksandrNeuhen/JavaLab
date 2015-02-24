package com.epam.testapp.database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.epam.testapp.database.connection.ConnectionPool;
import com.epam.testapp.database.impl.NewsDao;
import com.epam.testapp.exception.DaoException;
import com.epam.testapp.model.News;

public class TestNewsDAO {
	private static final String USERNAME = "testadmin";
	private static final String PASSWORD = "testroot";
	private static final String URL = "jdbc:oracle:thin:@//localhost:1521/xe";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String ENCODING = "UTF-8";
	private static final String USE_UNICODE = "true";
	private static final int CAPACITY = 10;
	
	private static final String SQL_INSERT_1 = "insert into news (title, brief, content, edition_date) "
			+ "values ('first title', 'first brief', 'first content', '11-OCT-2003')";
	private static final String SQL_INSERT_2 = "insert into news (title, brief, content, edition_date) "
			+ "values ('second title', 'second brief', 'second content', '12-OCT-2003')";
	private static final String SQL_INSERT_3 = "insert into news (title, brief, content, edition_date) "
			+ "values ('third title', 'third brief', 'third content', '13-OCT-2003')";
	private static final String SQL_DELETE_1 = "DELETE FROM news WHERE news_id=1 or news_id=2 or news_id=3";
	
	private static NewsDao dao = null;
	private static ConnectionPool cp = null;
	private static Connection conn = null;
	
	@BeforeClass
	public static void initClass(){
		try {
			cp = new ConnectionPool(USERNAME, PASSWORD, URL, CAPACITY, DRIVER, ENCODING, USE_UNICODE);
		} catch (DaoException e) {
			fail("Can't create connection pool");
		}
		dao = new NewsDao();
		dao.setConnectionPool(cp);
	}
	
	@AfterClass
	public static void destroyClass(){
		
	}
	
	@Before
	public void init() {		
		try {
			conn = cp.getConnection();
		} catch (DaoException e) {
			fail("Can't get connection");
		}
		
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			fail("Can't get statement");
		}
		
		try {
			st.executeUpdate(SQL_INSERT_1);
			st.executeUpdate(SQL_INSERT_2);
			st.executeUpdate(SQL_INSERT_3);
		} catch (SQLException e) {
			fail("Can't execute statement");
		} finally {
			try {
				try {
					cp.freeConnection(conn);
				} catch (DaoException e) {
					fail("Can't free connection");
				}
				st.close();
			} catch (SQLException e) {
				fail("Can't close statement");
			}
		}
	}
	
	@After
	public void destroy(){
		try {
			conn = cp.getConnection();
		} catch (DaoException e) {
			fail("Can't free connection");
		}
		
		Statement st = null;
		
		try {
			st = conn.createStatement();
			st.executeUpdate(SQL_DELETE_1);
		} catch(SQLException e){
			fail("Can't execute statement");
		} finally {
			try {
				st.close();
				try {
					cp.freeConnection(conn);
				} catch (DaoException e) {
					fail("Can't free connection");
				}
			} catch (SQLException e) {
				fail("Can't close statement");
			}
		}
		
	}
	
	@Test
	public void testGetList() {
		List<News> newsList = null;
		try {
			newsList = dao.getList();
		} catch (DaoException e) {
			fail("Can't get list of news");
		}
		assertFalse("News list must be filled from database", newsList.isEmpty());
	}
	
	@Test
	@Ignore
	public void testSave(){
		int [] newsToRemove = new int[] {1};
		try {
			dao.remove(newsToRemove);
		} catch (DaoException e) {
			fail("Can't remove news");
		}
		News news = new News();
		news.setTitle("saved title");
		news.setBrief("saved brief");
		news.setContent("saved content");
		try {
			news.setEditionDate(new SimpleDateFormat("MM/dd/yyyy").parse("11/11/2014"));
		} catch (ParseException e) {
			fail("Can't parse date");
		}
		try {
			dao.save(news);
		} catch (DaoException e) {
			fail("Can't save news");
		}
		try {
			news = dao.fetchByID(1);
		} catch (DaoException e) {
			fail("Can't fetch news by id");
		}
		assertEquals("saved title", news.getTitle());
	}
	
	@Test (expected = DaoException.class)
	public void testRemove() throws DaoException{
		int [] newsToRemove = new int[] {1, 2};
		try {
			dao.remove(newsToRemove);
		} catch (DaoException e) {
			fail("Can't remove news");
		}	
			dao.fetchByID(2);
	}
	
	@Test
	public void testFetchByID(){
		News news = null;
		try {
			news = dao.fetchByID(1);
		} catch (DaoException e) {
			fail("Can't fetch news by id");
		}
		assertNotNull(news);
	}
	
	@Test
	public void testUpdate(){
			News news = null;
			try {
				news = dao.fetchByID(1);
			} catch (DaoException e) {
				fail("Can't fetch news");
			}
			news.setTitle("edited title");
			try {
				dao.update(news);
			} catch (DaoException e) {
				fail("Can't update news");
			}
			
			try {
				news = dao.fetchByID(1);
			} catch (DaoException e) {
				fail("Can't fetch news");
			}
			assertEquals("edited title", news.getTitle());
	}
}
