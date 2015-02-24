package com.epam.testapp.database.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epam.testapp.database.INewsDao;
import com.epam.testapp.database.connection.ConnectionPool;
import com.epam.testapp.exception.DaoException;
import com.epam.testapp.model.News;
import com.epam.testapp.util.QueryGenerator;

/**
 * Implementation of INewsDao interface for working with news database. It is
 * using JDBC for interacting with database.
 */
public final class NewsDao implements INewsDao {

	// Database table columns

	private static final String FIELD_ID_COLUMN = "NEWS_ID";
	private static final String FIELD_TITLE_COLUMN = "TITLE";
	private static final String FIELD_BRIEF_COLUMN = "BRIEF";
	private static final String FIELD_CONTENT_COLUMN = "CONTENT";
	private static final String FIELD_EDITION_DATE_COLUMN = "EDITION_DATE";

	// SQL queries

	private static final String SQL_GET_NEWS_QUERY = "SELECT news_id, title, brief, content, edition_date FROM news ORDER BY edition_date DESC";
	private static final String SQL_GET_NEWS_BY_ID_QUERY = "SELECT news_id, title, brief, content, edition_date FROM news WHERE NEWS_ID = ?";
	private static final String SQL_SAVE_NEWS_QUERY = "INSERT INTO news (news_id, title, brief, content, edition_date) "
			+ "VALUES (NEWS_ID_SEQ.NEXTVAL, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_NEWS_QUERY = "UPDATE news SET title=?, brief=?, content=?, edition_date=? "
			+ "WHERE news_id = ?";

	private ConnectionPool connectionPool;

	/**
	 * Gets all news from database
	 * 
	 * @return news - ArrayList of all news
	 * @throws DaoException
	 *             if a database access error occurs
	 */
	@Override
	public List<News> getList() throws DaoException {
		List<News> news = new ArrayList<News>();
		News currNews = null;
		// Get connection from pool
		Connection conn = connectionPool.getConnection();
		try (Statement getNewsStatement = conn.createStatement();
				ResultSet newsSet = getNewsStatement
						.executeQuery(SQL_GET_NEWS_QUERY);) {
			// Fill list of news with news from ResultSet
			while (newsSet.next()) {
				int id = newsSet.getInt(FIELD_ID_COLUMN);
				String title = newsSet.getString(FIELD_TITLE_COLUMN);
				String brief = newsSet.getString(FIELD_BRIEF_COLUMN);
				String content = newsSet.getString(FIELD_CONTENT_COLUMN);
				Date editionDate = newsSet.getDate(FIELD_EDITION_DATE_COLUMN);
				currNews = new News(id, title, brief, content);
				currNews.setEditionDate(editionDate);
				news.add(currNews);
			}
			return news;
		} catch (SQLException e) {
			throw new DaoException(DaoExceptionMessage.EXC_LIST, e);
		} finally {
			connectionPool.freeConnection(conn);
		}
	}

	/**
	 * Saves created news to database
	 * 
	 * @param news
	 *            - bean to save
	 * @return id of saved news
	 * @throws DaoException
	 *             if a database access error occurs
	 */
	@Override
	public int save(News news) throws DaoException {
		int newsId = 0;
		ResultSet idResult = null;
		Connection conn = connectionPool.getConnection();
		try (PreparedStatement saveNewsStatement = conn.prepareStatement(
				SQL_SAVE_NEWS_QUERY, new String[] { FIELD_ID_COLUMN });) {

			saveNewsStatement.setString(1, news.getTitle());
			saveNewsStatement.setString(2, news.getBrief());
			saveNewsStatement.setString(3, news.getContent());
			java.util.Date date = news.getEditionDate();
			saveNewsStatement.setDate(4, new java.sql.Date(date.getTime()));

			saveNewsStatement.execute();
			idResult = saveNewsStatement.getGeneratedKeys();
			if (idResult.next()) {
				newsId = idResult.getInt(1);
			} else
				return 0;
		} catch (SQLException e) {
			throw new DaoException(DaoExceptionMessage.EXC_SAVE, e);
		} finally {
			if (idResult != null) {
				try {
					idResult.close();
				} catch (SQLException e) {
				}
			}
			connectionPool.freeConnection(conn);
		}

		// Return this ID
		return newsId;
	}

	/**
	 * Removes news by its ID from database. It uses batch for executing a
	 * several queries.
	 * 
	 * @param NewsToRemove
	 *            - array containing IDs of news
	 * @throws DaoException
	 *             if a database access error occurs
	 */
	@Override
	public void remove(int[] newsToRemove) throws DaoException {

		Connection conn = connectionPool.getConnection();
		try (Statement removeStatement = conn
				.createStatement();) {
			String deleteQuery = QueryGenerator.generateDeleteNewsQuery(newsToRemove);
			removeStatement.executeUpdate(deleteQuery.toString());
		} catch (SQLException e) {
			throw new DaoException(DaoExceptionMessage.EXC_DELETE, e);
		} finally {
			connectionPool.freeConnection(conn);
		}
	}

	/**
	 * Gets news by its ID from database
	 * 
	 * @param id
	 *            - ID of news to return
	 * @return News bean with corresponding ID.
	 * @return null if there are no news with corresponding ID in database
	 * @throws DaoException
	 *             if a database access error occurs
	 */
	@Override
	public News fetchByID(int id) throws DaoException {

		News news = new News();
		ResultSet result = null;
		Connection conn = connectionPool.getConnection();
		try (PreparedStatement getNewsByIdStatement = conn
				.prepareStatement(SQL_GET_NEWS_BY_ID_QUERY);) {

			getNewsByIdStatement.setInt(1, id);
			getNewsByIdStatement.executeQuery();
			result = getNewsByIdStatement.getResultSet();

			// Copy fetched news from ResultSet
			if (result.next()) {
				news.setId(id);
				news.setTitle(result.getString(FIELD_TITLE_COLUMN));
				news.setBrief(result.getString(FIELD_BRIEF_COLUMN));
				news.setContent(result.getString(FIELD_CONTENT_COLUMN));
				news.setEditionDate(result.getDate(FIELD_EDITION_DATE_COLUMN));
				return news;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DaoException(DaoExceptionMessage.EXC_FETCH, e);
		} finally {
			if (result != null) {
				try {
					result.close();
				} catch (SQLException e) {
				}
			}
			connectionPool.freeConnection(conn);

		}
	}

	/**
	 * Updates existing news in database
	 * 
	 * @param news
	 *            - News bean to update
	 * @throws DaoException
	 *             if a database access error occurs
	 */
	@Override
	public void update(News news) throws DaoException {

		Connection conn = connectionPool.getConnection();
		try (PreparedStatement updateNewsStatement = conn
				.prepareStatement(SQL_UPDATE_NEWS_QUERY)) {
			updateNewsStatement.setString(1, news.getTitle());
			updateNewsStatement.setString(2, news.getBrief());
			updateNewsStatement.setString(3, news.getContent());
			Long time = news.getEditionDate().getTime();
			java.sql.Date sqlDate = new java.sql.Date(time);
			updateNewsStatement.setDate(4, sqlDate);
			updateNewsStatement.setInt(5, news.getId());

			updateNewsStatement.execute();

		} catch (SQLException e) {
			throw new DaoException(DaoExceptionMessage.EXC_UPDATE, e);
		} finally {
			connectionPool.freeConnection(conn);
		}

	}

	public void setConnectionPool(ConnectionPool pool) {
		connectionPool = pool;
	}

}
