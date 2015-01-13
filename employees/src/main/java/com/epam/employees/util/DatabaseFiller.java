package com.epam.employees.util;

import java.util.Collection;

import com.epam.employees.dao.EmployeesDAO;
import com.epam.employees.dao.implementation.EmployeesDAOHibernate;
import com.epam.employees.entity.*;
import com.epam.employees.util.generator.DataGenerator;

public final class DatabaseFiller {
	public static void fillDatabase() {
		EmployeesDAO dao = new EmployeesDAOHibernate();
		
		Collection<City> cities = DataGenerator.generateCities(10);
		dao.saveCities(cities);
		
		Collection<Adress> generatedAdresses = DataGenerator.generateAdresses(20000);
		dao.saveAdresses(generatedAdresses);	
		
		Collection<Employee> generatedEmployees = DataGenerator.generateEmployees(10000);
		dao.saveEmployees(generatedEmployees);
		
		Collection<Company> generatedCompanies = DataGenerator.generateCompaniesFromFile(100);
		dao.saveCompanies(generatedCompanies);
		
		Collection<Office> generatedOffices = DataGenerator.generateOffices(5000);
		dao.saveOffices(generatedOffices);
		
		Collection<Position> generatedPositions = DataGenerator.generatePositions(12000);
		dao.savePositions(generatedPositions);
	}
}
