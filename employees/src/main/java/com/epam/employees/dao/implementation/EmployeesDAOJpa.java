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

public class EmployeesDAOJpa implements EmployeesDAO {

	public EntityManager entityManager = Persistence.createEntityManagerFactory("emp")
			.createEntityManager();
	
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

	public List<Employee> getEmployees(Integer firstResult,
			Integer countOfResults) {
		return getData(Employee.class, firstResult, countOfResults);
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
	
	public <T> List<T> getData(Class<T> persistenceClass, Integer firstResult, Integer maxResults) {
		EntityTransaction transaction = entityManager.getTransaction(); 
		transaction.begin();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(persistenceClass);
		Root<T> rootEntry = criteriaQuery.from(persistenceClass);
		rootEntry.fetch("adress").fetch("city").fetch("country");
		CriteriaQuery<T> list = criteriaQuery.select(rootEntry);
		TypedQuery<T> listQuery = entityManager.createQuery(list);
		listQuery.setFirstResult(firstResult);
		listQuery.setMaxResults(maxResults);
		List<T> data = listQuery.getResultList(); 
		transaction.commit();
		return data;		
	}

}
