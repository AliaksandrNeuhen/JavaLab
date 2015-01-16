package com.epam.employees.dao.implementation;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.epam.employees.dao.EmployeesDAO;
import com.epam.employees.entity.Adress;
import com.epam.employees.entity.City;
import com.epam.employees.entity.Company;
import com.epam.employees.entity.Country;
import com.epam.employees.entity.Employee;
import com.epam.employees.entity.Office;
import com.epam.employees.entity.Position;

public final class EmployeesDAOJpa implements EmployeesDAO {

	private static final String EMPLOYEES_PU = "emp";
	private static final String ADRESS_FIELD = "adress";
	private static final String CITY_FIELD = "city";
	private static final String COUNTRY_FIELD = "country";
	
	private final static EntityManager entityManager = Persistence
			.createEntityManagerFactory(EMPLOYEES_PU).createEntityManager();

	public List<Employee> getEmployees(Integer firstResult,
			Integer countOfResults) {
		return getData(Employee.class, firstResult, countOfResults);
	}

	public <T> List<T> getData(Class<T> persistenceClass, Integer firstResult, Integer maxResults) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// Get criteria builder used to construct criteria queries
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		// Create criteria query
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(persistenceClass);
		// Create and add a query root corresponding to the given entity
		Root<T> rootEntry = criteriaQuery.from(persistenceClass);
		// Create a fetch join to the adress, city and country attributes
		// using an inner join
		rootEntry.fetch(ADRESS_FIELD).fetch(CITY_FIELD).fetch(COUNTRY_FIELD);
		// Specify the item that is to be returned in the query result
		CriteriaQuery<T> list = criteriaQuery.select(rootEntry);
		// Create an instance of TypedQuery for executing a criteria query
		TypedQuery<T> listQuery = entityManager.createQuery(list);
		// Set first result and amount of results to fetch
		listQuery.setFirstResult(firstResult);
		listQuery.setMaxResults(maxResults);
		// Execute a SELECT query and get the query results as a list
		List<T> data = listQuery.getResultList();
		transaction.commit();
		return data;
	}

	public List<Employee> getEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Position> getPositions() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Office> getOffices() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Country> getCountries() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<City> getCities() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Adress> getAdresses() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Company> getCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveCities(Collection<City> cities) {
		// TODO Auto-generated method stub

	}

	public void saveAdresses(Collection<Adress> adresses) {
		// TODO Auto-generated method stub

	}

	public void saveEmployees(Collection<Employee> employees) {
		// TODO Auto-generated method stub

	}

	public void saveCompanies(Collection<Company> companies) {
		// TODO Auto-generated method stub

	}

	public void saveOffices(Collection<Office> offices) {
		// TODO Auto-generated method stub

	}

	public void savePositions(Collection<Position> positions) {
		// TODO Auto-generated method stub

	}
}
