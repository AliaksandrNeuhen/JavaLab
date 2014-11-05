package com.epam.testapp.database.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;

import com.epam.testapp.database.INewsDao;
import com.epam.testapp.model.News;

/**
 * INewsDao interface realization using Java Persistence API.
 */

public final class NewsDaoJpa implements INewsDao {
	private static final String ORDER_BY_FIELD = "editionDate"; 
	private static final String ID_LIST_QUERY_PARAMETER = "idList";
	private static final String JPQL_DELETE_NAMED_QUERY = "News.delete";

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Gets list of news sorted by descending date of edition.
	 * 
	 * @return list of news sorted by descending date of edition.
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<News> getList() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<News> criteriaQuery = criteriaBuilder
				.createQuery(News.class);
		Root<News> newsRoot = criteriaQuery.from(News.class);
		criteriaQuery.select(newsRoot);
		Path<News> datePath = newsRoot.get(ORDER_BY_FIELD);
		Order descendingByDate = criteriaBuilder.desc(datePath);
		criteriaQuery.orderBy(descendingByDate);
		Query getListQuery = entityManager.createQuery(criteriaQuery);
		List<News> news = getListQuery.getResultList();
		return news;
	}

	/**
	 * Saves created news
	 * 
	 * @param news
	 *            - bean to save
	 * @return id of saved news
	 */

	@Override
	@Transactional
	public int save(News news) {
		entityManager.persist(news);
		return news.getId();
	}

	/**
	 * Removes news by its ID
	 * 
	 * @param NewsToRemove
	 *            - array containing IDs of news
	 */

	@Override
	@Transactional
	public void remove(int[] newsToRemove) {
		Query deleteQuery = entityManager
				.createNamedQuery(JPQL_DELETE_NAMED_QUERY);
		// Transform array of IDs into list of Integers
		Integer[] ids = ArrayUtils.toObject(newsToRemove);
		List<Integer> idList = Arrays.asList(ids);
		deleteQuery.setParameter(ID_LIST_QUERY_PARAMETER, idList);
		deleteQuery.executeUpdate();
	}

	/**
	 * Gets news by its ID
	 * 
	 * @param id
	 *            - ID of news to return
	 * @return News bean with corresponding ID
	 * @return null if News bean with corresponding ID was not found
	 */

	@Override
	public News fetchByID(int id) {
		News resultNews = entityManager.find(News.class, id);
		return resultNews;
	}

	/**
	 * Updates existing news
	 * 
	 * @param news
	 *            - News bean to update
	 */

	@Override
	@Transactional
	public void update(News news) {
		entityManager.merge(news);
	}
}
