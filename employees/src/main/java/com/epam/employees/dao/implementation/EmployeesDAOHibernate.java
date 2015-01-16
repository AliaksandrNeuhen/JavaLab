package com.epam.employees.dao.implementation;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.epam.employees.dao.EmployeesDAO;
import com.epam.employees.entity.Adress;
import com.epam.employees.entity.City;
import com.epam.employees.entity.Company;
import com.epam.employees.entity.Country;
import com.epam.employees.entity.Employee;
import com.epam.employees.entity.Office;
import com.epam.employees.entity.Position;
import com.epam.employees.util.HibernateUtil;

/**
 * Data Access object for interaction with employees database
 *
 */
public final class EmployeesDAOHibernate implements EmployeesDAO {
	
	public List<Position> getPositions() {
		return getData(Position.class);
	}
	public List<Employee> getEmployees() {
		return getData(Employee.class);
	}
	public List<Office> getOffices() {
		return getData(Office.class);
	}
	public List<Country> getCountries() {
		return getData(Country.class);
	}
	public List<City> getCities() {
		return getData(City.class);
	}
	public List<Adress> getAdresses() {
		return getData(Adress.class);
	}
	public List<Company> getCompanies() {
		return getData(Company.class);
	}
	
	/**
	 * Get amount of employees from database starting with some index
	 * @param firstResult - index of the first employee to fetch
	 * @param countOfResults - amount of employees to fetch
	 */
	public List<Employee> getEmployees(Integer firstResult, Integer countOfResults) {
		return getData(Employee.class, firstResult, countOfResults);
	}
	
	public void saveCities(Collection<City> cities) {
		saveData(cities);
	}
	public void saveCountries(Collection<Country> countries) {
		saveData(countries);
	}
	public void saveAdresses(Collection<Adress> adresses) {
		saveData(adresses);
	}
	public void saveEmployees(Collection<Employee> employees) {
		saveData(employees);
	}
	public void saveCompanies(Collection<Company> companies) {
		saveData(companies);
	}
	public void saveOffices(Collection<Office> offices) {
		saveData(offices);
	}
	public void savePositions(Collection<Position> positions) {
		saveData(positions);
	};
	
	/**
	 * Generic method for fetching all data of chosen class from database
	 * @param persistentClass - class of objects to fetch
	 * @return list of objects of chosen class fetched from database
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> getData(Class<T> persistentClass) {
		List<T> data = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			Criteria criteria = session.createCriteria(persistentClass);
			data = criteria.list();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Get amount of objects from database starting with some index.
	 * @param persistentClass - class of objects to fetch
	 * @param firstResult - index of the first object to fetch
	 * @param countOfResults - amount of objects to fetch
	 * @return list of fetched objects
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> getData(Class<T> persistentClass, int firstResult,
			int countOfResults) {
		List<T> data = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.getTransaction();
			transaction.begin();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(countOfResults);
			data = criteria.list();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Persist collection of objects in database
	 * @param data - collection of objects
	 */
	private <T> void saveData(Collection<T> data) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
		try {
			for (T entity: data) {
				session.save(entity);
			}
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}
}