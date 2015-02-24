<<<<<<< HEAD:testappService/src/main/java/com/epam/testappservice/service/impl/NewsService.java
package com.epam.testappservice.service.impl;

import java.util.ArrayList;
import java.util.List;

=======
package com.epam.testapp.service.impl;

import java.util.ArrayList;
import java.util.List;

>>>>>>> ololo:testapp/src/main/java/com/epam/testapp/service/impl/NewsService.java
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;

<<<<<<< HEAD:testappService/src/main/java/com/epam/testappservice/service/impl/NewsService.java
import com.epam.testappservice.database.INewsDao;
import com.epam.testappservice.database.impl.NewsDao;
import com.epam.testappservice.database.impl.NewsDaoHibernate;
import com.epam.testappservice.exception.DaoException;
import com.epam.testappservice.exception.FetchByIdException;
import com.epam.testappservice.exception.ServiceException;
import com.epam.testappservice.model.News;
import com.epam.testappservice.service.INewsService;

/**
 * Service for interaction between NewsAction and INewsDAO
 */
@WebService(endpointInterface = "com.epam.testappservice.service.INewsService",
		serviceName = "newsService")
public class NewsService implements INewsService {
	private static final String EXC_FETCH = "There are no news with entered ID";

	private INewsDao newsDao = new NewsDaoHibernate();

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
	public NewsService() {
	}
	
	public News fetchByID(int id) throws ServiceException, FetchByIdException {
		News news;
		try {
			news = newsDao.fetchByID(id);
		} catch (DaoException e) {
			throw new ServiceException(EXC_FETCH, e);
		}

		// If News with corresponding ID not found
		if (news == null) {
			throw new FetchByIdException(EXC_FETCH);
		}

		return news;
	}

	/**
	 * Gets a list of News from DAO
	 * 
	 * @return list of all news from DAO
	 * @return empty list if there are no news
	 * @throws ServiceException
	 *             when something wrong with DAO
	 */
	public List<News> getList() throws ServiceException {
		List<News> newsList = null;
		try {
			newsList = newsDao.getList();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		if (newsList == null) {
			return new ArrayList<News>();
		}
		return newsList;
	}

	/**
	 * Removes News with corresponding IDs from DAO
	 * 
	 * @param newsToDelete
	 *            - array with IDs of News to be deleted
	 * @throws ServiceException
	 *             when something wrong with DAO
	 */
	public void remove(int[] newsToDelete) throws ServiceException {
		try {
			newsDao.remove(newsToDelete);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

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
	public int save(News newsToSave) throws ServiceException {
		int id = newsToSave.getId();
		try {
			if (id != 0) {
				newsDao.update(newsToSave);
			} else {
				id = newsDao.save(newsToSave);
				newsToSave.setId(id);
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return id;
	}

	public void setNewsDao(INewsDao newsDao) {
		this.newsDao = newsDao;
	}
}
=======
import com.epam.testapp.database.INewsDao;
import com.epam.testapp.database.impl.NewsDaoHibernate;
import com.epam.testapp.exception.DaoException;
import com.epam.testapp.exception.FetchByIdException;
import com.epam.testapp.exception.ServiceException;
import com.epam.testapp.model.News;
import com.epam.testapp.service.INewsService;

/**
 * Service for interaction between NewsAction and INewsDAO
 */
@WebService(endpointInterface = "com.epam.testapp.service.INewsService",
		serviceName = "newsService")
public class NewsService implements INewsService {
	private static final String EXC_FETCH = "There are no news with entered ID";

	private INewsDao newsDao = new NewsDaoHibernate();

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
	public NewsService() {
	}

	public News fetchByID(int id) throws ServiceException, FetchByIdException {
		News news;
		try {
			news = newsDao.fetchByID(id);
		} catch (DaoException e) {
			throw new ServiceException(EXC_FETCH, e);
		}

		// If News with corresponding ID not found
		if (news == null) {
			throw new FetchByIdException(EXC_FETCH);
		}

		return news;
	}

	/**
	 * Gets a list of News from DAO
	 * 
	 * @return list of all news from DAO
	 * @return empty list if there are no news
	 * @throws ServiceException
	 *             when something wrong with DAO
	 */
	public List<News> getList() throws ServiceException {
		List<News> newsList = null;
		try {
			newsList = newsDao.getList();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		if (newsList == null) {
			return new ArrayList<News>();
		}
		return newsList;
	}

	/**
	 * Removes News with corresponding IDs from DAO
	 * 
	 * @param newsToDelete
	 *            - array with IDs of News to be deleted
	 * @throws ServiceException
	 *             when something wrong with DAO
	 */
	public void remove(int[] newsToDelete) throws ServiceException {
		try {
			newsDao.remove(newsToDelete);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

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
	public int save(News newsToSave) throws ServiceException {
		int id = newsToSave.getId();
		try {
			if (id != 0) {
				newsDao.update(newsToSave);
			} else {
				id = newsDao.save(newsToSave);
				newsToSave.setId(id);
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return id;
	}

	public void setNewsDao(INewsDao newsDao) {
		this.newsDao = newsDao;
	}
}
>>>>>>> ololo:testapp/src/main/java/com/epam/testapp/service/impl/NewsService.java
