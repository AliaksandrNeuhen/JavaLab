package com.epam.testapp.database.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import com.epam.testapp.database.INewsDao;
import com.epam.testapp.exception.DaoException;
import com.epam.testapp.model.News;
import com.epam.testapp.util.HibernateUtil;

/**
 * INewsDao interface realization using Hibernate Framework
 */
public final class NewsDaoHibernate implements INewsDao {
	private static final String SORTING_FIELD = "editionDate";
	private static final String PROJECTION_FIELD = "id";
	private static final String ID_LIST_PARAMETER = "idList";
	private static final String NAMED_QUERY_DELETE_NEWS = "deleteNews";

	/**
	 * Gets list of news sorted by descending date of edition.
	 * 
	 * @return list of news sorted by descending date of edition.
	 * @throws DaoException
	 *             if Hibernate error occurs
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<News> getList() throws DaoException {
		Session session = null;
		List<News> news = new LinkedList<News>();
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(News.class);
			Order sortingOrder = Order.desc(SORTING_FIELD);
			criteria.addOrder(sortingOrder);
			news = criteria.list();
			tx.commit();
		} catch (HibernateException e) {
			throw new DaoException(DaoExceptionMessage.EXC_LIST, e);
		}
		return news;
	}

	/**
	 * Saves created news
	 * 
	 * @param news
	 *            - bean to save
	 * @return id of saved news
	 * @throws DaoException
	 *             if Hibernate error occurs
	 */

	@Override
	public int save(News news) throws DaoException {
		Session session = null;
		int id = 0;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			session.save(news);
			// Get last saved news's ID to return
			Criteria max = session.createCriteria(News.class);
			max.setProjection(Projections.max(PROJECTION_FIELD));
			id = (int) max.uniqueResult();
			tx.commit();
		} catch (HibernateException e) {
			throw new DaoException(DaoExceptionMessage.EXC_SAVE, e);
		}
		return id;
	}

	/**
	 * Removes news by its ID
	 * 
	 * @param NewsToRemove
	 *            - array containing IDs of news
	 * @throws DaoException
	 *             if Hibernate error occurs
	 */

	@Override
	public void remove(int[] newsToRemove) throws DaoException {
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			Query deleteQuery = session.getNamedQuery(NAMED_QUERY_DELETE_NEWS);
			Integer[] ids = ArrayUtils.toObject(newsToRemove);
			deleteQuery.setParameterList(ID_LIST_PARAMETER, ids);
			deleteQuery.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			throw new DaoException(DaoExceptionMessage.EXC_DELETE, e);
		}
	}

	/**
	 * Gets news by its ID
	 * 
	 * @param id
	 *            - ID of news to return
	 * @return News bean with corresponding ID
	 * @return null if News bean with corresponding ID was not found
	 * @throws DaoException
	 *             if Hibernate error occurs
	 */

	@Override
	public News fetchByID(int id) throws DaoException {
		News news = null;

		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			news = (News) session.get(News.class, id);
			tx.commit();
		} catch (HibernateException e) {
			throw new DaoException(DaoExceptionMessage.EXC_FETCH, e);
		}
		return news;
	}

	/**
	 * Updates existing news
	 * 
	 * @param news
	 *            - News bean to update
	 * @throws DaoExceptionif
	 *             Hibernate error occurs
	 */

	@Override
	public void update(News news) throws DaoException {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			session.update(news);
			tx.commit();
		} catch (HibernateException e) {
			throw new DaoException(DaoExceptionMessage.EXC_UPDATE, e);
		}
	}
}