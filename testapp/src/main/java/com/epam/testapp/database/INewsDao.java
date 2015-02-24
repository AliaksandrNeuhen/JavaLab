package com.epam.testapp.database;

import java.util.List;

import com.epam.testapp.exception.DaoException;
import com.epam.testapp.model.News;

/**
 * Interface for working with news data
 */
public interface INewsDao {

	/**
	 * Gets list of news
	 * 
	 * @return list of news
	 * @throws DaoException
	 */

	List<News> getList() throws DaoException;

	/**
	 * Saves created news
	 * 
	 * @param news
	 *            - bean to save
	 * @return id of saved news
	 * @throws DaoException
	 */

	int save(News news) throws DaoException;

	/**
	 * Removes news by its ID
	 * 
	 * @param NewsToRemove
	 *            - array containing IDs of news
	 * @throws DaoException
	 */

	void remove(int[] NewsToRemove) throws DaoException;

	/**
	 * Gets news by its ID
	 * 
	 * @param id
	 *            - ID of news to return
	 * @return News bean with corresponding ID
	 * @throws DaoException
	 */

	News fetchByID(int id) throws DaoException;

	/**
	 * Updates existing news
	 * 
	 * @param news
	 *            - News bean to update
	 * @throws DaoException
	 */

	void update(News news) throws DaoException;
}
