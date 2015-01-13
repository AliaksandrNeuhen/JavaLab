package com.epam.employees.util.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.fluttercode.datafactory.impl.DataFactory;

import com.epam.employees.dao.EmployeesDAO;
import com.epam.employees.dao.implementation.EmployeesDAOHibernate;
import com.epam.employees.entity.Adress;
import com.epam.employees.entity.City;
import com.epam.employees.entity.Company;
import com.epam.employees.entity.Country;
import com.epam.employees.entity.Employee;
import com.epam.employees.entity.Office;
import com.epam.employees.entity.Position;

public class DataGenerator {
	private static final String COMPANIES_FILE = "D:\\companies.txt";
	private static final String POSITIONS_FILE = "D:\\positions.txt";
	
	private final static DataFactory dataFactory = new DataFactory();
	
	/**
	 * Generates collection containing cities.
	 * @param numberOfCities - number of cities to generate
	 * @return list of unique city objects
	 */
	public static Collection<City> generateCities(int numberOfCities) {
		EmployeesDAO dao = new EmployeesDAOHibernate();
		List<Country> existingCountries = dao.getCountries();
		List<City> generatedCities = new ArrayList<City>();
		
		City generatedCity;
		Country selectedCountry;
		int numberOfCountries = existingCountries.size();
		while (generatedCities.size() < numberOfCities) {
			String cityName = dataFactory.getCity();
			int countryIndex = dataFactory.getNumberBetween(0, numberOfCountries - 1);
			selectedCountry = existingCountries.get(countryIndex);
			generatedCity = new City(cityName, selectedCountry);
			generatedCities.add(generatedCity);
		}
		return generatedCities;
	}
	
	public static Collection<Adress> generateAdresses(int numberOfAdresses) {
		EmployeesDAO dao = new EmployeesDAOHibernate();
		List<City> existingCities = dao.getCities();
		Collection<Adress> generatedAdresses = new ArrayList<Adress>();	
		
		int numberOfCities = existingCities.size();
		City selectedCity;
		Adress generatedAdress;
		Random random = new Random();
		while (generatedAdresses.size() < numberOfAdresses) {
			String adressContent = dataFactory.getAddress();
			int cityIndex = random.nextInt(numberOfCities - 1);
			selectedCity = existingCities.get(cityIndex);
			generatedAdress = new Adress(adressContent, selectedCity);
			generatedAdresses.add(generatedAdress);
		}
		return generatedAdresses;
	}
	
	public static Collection<Employee> generateEmployees(int numberOfEmployees) {
		EmployeesDAO dao = new EmployeesDAOHibernate();
		List<Adress> existingAdresses = dao.getAdresses();
		Collection<Employee> generatedEmployees = new ArrayList<Employee>();
		
		int numberOfAdresses = existingAdresses.size();
		Employee generatedEmployee = null;
		Adress selectedAdress = null;
		Random random = new Random();
		while (generatedEmployees.size() < numberOfEmployees) {
			int adressIndex = random.nextInt(numberOfAdresses - 1);
			selectedAdress = existingAdresses.get(adressIndex);
			String firstName = dataFactory.getFirstName();
			String lastName = dataFactory.getLastName();
			generatedEmployee = new Employee(firstName, lastName, selectedAdress);
			generatedEmployees.add(generatedEmployee);
		}
		return generatedEmployees;
	}
	
	public static Collection<Office> generateOffices(int numberOfOffices) {
		EmployeesDAO dao = new EmployeesDAOHibernate();
		List<Adress> existingAdresses = dao.getAdresses();
		List<Company> existingCompanies = dao.getCompanies();
		List<Office> generatedOffices = new ArrayList<Office>();
		
		int numberOfAdresses = existingAdresses.size();
		int numberOfCompanies = existingCompanies.size();
		Adress selectedAdress = null;
		Company selectedCompany = null;
		Office generatedOffice = null;
		Random random = new Random();
		while (generatedOffices.size() < numberOfOffices) {
			int adressIndex = random.nextInt(numberOfAdresses - 1);
			int companyIndex = random.nextInt(numberOfCompanies - 1);
			selectedAdress = existingAdresses.get(adressIndex);
			selectedCompany = existingCompanies.get(companyIndex);
			String description = dataFactory.getRandomText(50, 90);
			generatedOffice = new Office( description, selectedAdress, selectedCompany);
			generatedOffices.add(generatedOffice);
		}
		return generatedOffices;
	}
	
	public static Collection<Position> generatePositions(int countOfPositions) {
		EmployeesDAO dao = new EmployeesDAOHibernate();
		List<Office> existingOffices = dao.getOffices();
		List<Employee> existingEmployees = dao.getEmployees();
		List<String> positionNames = getNamesList();
		List<Position> generatedPositions = new LinkedList<Position>();
		
		Office selectedOffice = null;
		Employee selectedEmployee = null;
		Position generatedPosition = null;
		int countOfOffices = existingOffices.size();
		int countOfEmployees = existingEmployees.size();
		int countOfPositionName = positionNames.size();
		Random random = new Random();
		while (generatedPositions.size() < countOfPositions) {
			int officeIndex = random.nextInt(countOfOffices - 1);
			int employeeIndex = random.nextInt(countOfEmployees - 1);
			int positionNameIndex = random.nextInt(countOfPositionName - 1);
			selectedOffice = existingOffices.get(officeIndex);
			selectedEmployee = existingEmployees.get(employeeIndex);
			String positionName = positionNames.get(positionNameIndex);
			generatedPosition = new Position(positionName, selectedOffice, selectedEmployee);
			generatedPositions.add(generatedPosition);
		}
		return generatedPositions;
	}
	
	public static Collection<Company> generateCompaniesFromFile(int numberOfCompanies) {
		File companiesFile = new File(COMPANIES_FILE);
		BufferedReader reader = null;
		Collection<Company> generatedCompanies = new ArrayList<Company>();
		try {
			reader = new BufferedReader(new FileReader(companiesFile));
			Company generatedCompany;
			while (reader.ready() && generatedCompanies.size() < numberOfCompanies) {
				String companyName = reader.readLine();
				generatedCompany = new Company(companyName);
				if (generatedCompanies.contains(generatedCompany)){
					continue;
				} else {
					generatedCompanies.add(generatedCompany);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {}
		}
		return generatedCompanies;
	}
	
	private static List<String> getNamesList() {
		List<String> names = new ArrayList<String>();
		File positionsFile = new File(POSITIONS_FILE);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(positionsFile));
			while (reader.ready()) {
				String currentName = reader.readLine();
				if (!names.contains(currentName)) {
					names.add(currentName);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {}
		}
		return names;
	}
}



