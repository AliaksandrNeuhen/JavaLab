package com.epam.employees.util;

import java.util.Collection;

import com.epam.employees.dao.EmployeesDAO;
import com.epam.employees.dao.implementation.EmployeesDAOHibernate;
import com.epam.employees.entity.*;
import com.epam.employees.util.generator.DataGenerator;

/**
 * Utility class for filling employees database with random values.
 * Filling countries table is not implemented.
 * It uses Hibernate DAO for filling database.
 */
public final class DatabaseFiller {
	public static void fillDatabase() {
		EmployeesDAO dao = new EmployeesDAOHibernate();
		
		// Generate collection of cities and persist it in database
		Collection<City> cities = DataGenerator.generateCities(10);
		dao.saveCities(cities);
		
		// Generate collection of adresses and persist it in database
		Collection<Adress> generatedAdresses = DataGenerator.generateAdresses(20000);
		dao.saveAdresses(generatedAdresses);	
		
		// Generate collection of employees and persist it in database
		Collection<Employee> generatedEmployees = DataGenerator.generateEmployees(10000);
		dao.saveEmployees(generatedEmployees);
		
		// Get collection of companies from text file and persist it in database
		Collection<Company> generatedCompanies = DataGenerator.generateCompaniesFromFile(100);
		dao.saveCompanies(generatedCompanies);
		
		// Generate collection of offices and persist it in database
		Collection<Office> generatedOffices = DataGenerator.generateOffices(5000);
		dao.saveOffices(generatedOffices);
		
		// Generate collection of positions and persist it in database
		Collection<Position> generatedPositions = DataGenerator.generatePositions(12000);
		dao.savePositions(generatedPositions);
	}
}
