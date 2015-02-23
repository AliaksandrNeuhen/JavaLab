package com.epam.testappview.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.epam.testappview.exception.FetchByIdException;
import com.epam.testappview.exception.ServiceException;
import com.epam.testappview.model.News;

/**
 * Interface for interaction between NewsAction and INewsDao
 */
//@Local
@WebService
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
	@WebMethod
	News fetchByID(int id) throws ServiceException, FetchByIdException;

	/**
	 * Gets a list of News from DAO
	 * 
	 * @return list of all news from DAO
	 * @return empty list if there are no news
	 * @throws ServiceException
	 *             when something wrong with DAO
	 */
	@WebMethod
	List<News> getList() throws ServiceException;

	/**
	 * Removes News with corresponding IDs from DAO
	 * 
	 * @param newsToDelete
	 *            - array with IDs of News to be deleted
	 * @throws ServiceException
	 *             when something wrong with DAO
	 */
	@WebMethod
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
	@WebMethod
	int save(News newsToSave) throws ServiceException;
}
