package com.epam.testapp.service;

import java.util.List;

import com.epam.testapp.exception.FetchByIdException;
import com.epam.testapp.exception.ServiceException;
import com.epam.testapp.model.News;

/**
 * Interface for interaction between NewsAction and INewsDao
 */
public interface INewsService {

	/**
	 * Gets a News bean from DAO by ID
	 * 
	 * @param id
	 *            - id of required News
	 * @return News bean with corresponding ID
	 * @throws ServiceException
	 *             when something wrong with DAO
	 * @throws FetchByIdException
	 *             if news with corresponding ID not found
	 */
	News fetchByID(int id) throws ServiceException, FetchByIdException;

	/**
	 * Gets a list of News from DAO
	 * 
	 * @return list of all news from DAO
	 * @return empty list if there are no news
	 * @throws ServiceException
	 *             when something wrong with DAO
	 */
	List<News> getList() throws ServiceException;

	/**
	 * Removes News with corresponding IDs from DAO
	 * 
	 * @param newsToDelete
	 *            - array with IDs of News to be deleted
	 * @throws ServiceException
	 *             when something wrong with DAO
	 */
	void remove(int[] newsToDelete) throws ServiceException;

	/**
	 * Saves news to DAO. If id of News bean sent as parameter == 0, then
	 * creates new persistent object. If id != 0, then updates existing object.
	 * 
	 * @param newsToSave
	 *            News bean to be saved
	 * @return ID of saved news
	 * @throws ServiceException
	 *             when something wrong with DAO
	 */
	int save(News newsToSave) throws ServiceException;
}
