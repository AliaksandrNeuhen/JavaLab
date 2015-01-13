package com.epam.employees.dao;

import java.util.Collection;
import java.util.List;

import com.epam.employees.entity.Adress;
import com.epam.employees.entity.City;
import com.epam.employees.entity.Company;
import com.epam.employees.entity.Country;
import com.epam.employees.entity.Employee;
import com.epam.employees.entity.Office;
import com.epam.employees.entity.Position;

public interface EmployeesDAO {
	public List<Employee> getEmployees();
	public List<Position> getPositions();
	public List<Office> getOffices();
	public List<Country> getCountries();
	public List<City> getCities(); 
	public List<Adress> getAdresses(); 
	public List<Company> getCompanies();
	
	public List<Employee> getEmployees(Integer firstResult, Integer countOfResults); 
	
	public void saveCities(Collection<City> cities);
	public void saveAdresses(Collection<Adress> adresses);
	public void saveEmployees(Collection<Employee> employees);
	public void saveCompanies(Collection<Company> companies);
	public void saveOffices(Collection<Office> offices);
	public void savePositions(Collection<Position> positions);
}
