package com.epam.employees.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.epam.employees.dao.EmployeesDAO;
import com.epam.employees.dao.implementation.pool.ConnectionPool;
import com.epam.employees.entity.Adress;
import com.epam.employees.entity.City;
import com.epam.employees.entity.Company;
import com.epam.employees.entity.Country;
import com.epam.employees.entity.Employee;
import com.epam.employees.entity.Office;
import com.epam.employees.entity.Position;
import com.epam.employees.exception.CommandException;

public final class EmployeesDAOJdbc implements EmployeesDAO {

	// Get list of employees

	private static final String GET_EMPL_SQL =
			"		select *"
			+ "		from ("
			+ "			select row_.*, rownum rownum_"
			+ "			from ("
			+ " 	 select emp2.ID_EMPLOYEE as EMPLOYEE_ID,"
			+ "		emp2.FIRST_NAME as FIRST_NAME, "
			+ "		emp2.LAST_NAME as LAST_NAME, "
			+ "		adr2.CONTENT as EMPLOYEE_ADRESS, "
			+ "		ct2.NAME as EMPLOYEE_ADRESS_CITY, "
			+ "		ctr2.NAME as EMPLOYEE_ADRESS_COUNTRY"
			+ "				from EMPLOYEE emp2"
			+ "				inner join ADRESS adr2 on emp2.FK_ID_ADRESS = adr2.ID_ADRESS"
			+ "				inner join CITY ct2 on adr2.FK_ID_CITY = ct2.ID_CITY"
			+ "				inner join COUNTRY ctr2 on ct2.FK_ID_COUNTRY = ctr2.ID_COUNTRY"
			+ "			) row_)"
			+ "		where rownum_ <= ? and rownum_ > ?"
			+ "		order by EMPLOYEE_ID";

	// Get positions for employees

	private static final String GET_POS_SQL = "select pos.ID_POSITION as POS_ID,"
			+ "        pos.NAME as POS_NAME,"
			+ "        emp.FIRST_NAME as FIRST_NAME,"
			+ "        emp.LAST_NAME as LAST_NAME,"
			+ "        pos.FK_ID_OFFICE as OFFICE_ID,"
			+ "        pos.FK_ID_EMPLOYEE as EMPLOYEE_ID,"
			+ "        adr1.CONTENT as ADRESS,"
			+ "        ct.NAME as CITY,"
			+ "        ctr.NAME as COUNTRY,"
			+ "        offc.ID_OFFICE as OFFICE_ID2,"
			+ "        (select count(*) "
			+ "          from position p "
			+ "          where p.FK_ID_OFFICE = offc.ID_OFFICE) as COUNT_OF_EMP,"
			+ "        cmp.name as COMPANY_NAME,"
			+ "        adr2.CONTENT as EMPLOYEE_ADRESS,"
			+ "        ct2.NAME as EMPLOYEE_ADRESS_CITY,"
			+ "        ctr.NAME as EMPLOYEE_ADRESS_COUNTRY,"
			+ "        offc.DESCRIPTION as OFFICE_DESCRIPTION"
			+ "        from POSITION pos"
			+ "        join OFFICE offc on offc.ID_OFFICE = pos.FK_ID_OFFICE"
			+ "        join ADRESS adr1 on offc.FK_ID_ADRESS = adr1.ID_ADRESS"
			+ "        join CITY ct on adr1.FK_ID_CITY = ct.ID_CITY"
			+ " join COUNTRY ctr on ct.FK_ID_COUNTRY = ctr.ID_COUNTRY"
			+ " join COMPANY cmp on offc.FK_ID_COMPANY = cmp.ID_COMPANY"
			+ " join EMPLOYEE emp on pos.FK_ID_EMPLOYEE = emp.ID_EMPLOYEE"
			+ " join ADRESS adr2 on emp.FK_ID_ADRESS = adr2.ID_ADRESS"
			+ " join CITY ct2 on adr2.FK_ID_CITY = ct2.ID_CITY"
			+ " join COUNTRY ctr2 on ct2.FK_ID_COUNTRY = ctr2.ID_COUNTRY"
			+ " where pos.FK_ID_EMPLOYEE in"
			+ "  (?)"
			+ " order by emp.ID_EMPLOYEE";
	
	// Column names
	
	private static final String POSITION_NAME_COLUMN = "POS_NAME";
	private static final String FIRST_NAME_COLUMN = "FIRST_NAME";
	private static final String LAST_NAME_COLUMN = "LAST_NAME";
	private static final String EMPLOYEE_ID_COLUMN = "EMPLOYEE_ID";
	private static final String OFFICE_ADRESS_COLUMN = "ADRESS";
	private static final String CITY_COLUMN = "CITY";
	private static final String COUNTRY_COLUMN = "COUNTRY";
	private static final String COUNT_OF_EMPLOYEES_COLUMN = "COUNT_OF_EMP";
	private static final String OFFICE_DESCRIPTION_COLUMN = "OFFICE_DESCRIPTION";
	private static final String COMPANY_NAME_COLUMN = "COMPANY_NAME";
	private static final String EMPLOYEE_ADRESS_COLUMN = "EMPLOYEE_ADRESS";
	private static final String EMPLOYEE_ADRESS_CITY_COLUMN = "EMPLOYEE_ADRESS_CITY";
	private static final String EMPLOYEE_ADRESS_COUNTRY_COLUMN = "EMPLOYEE_ADRESS_COUNTRY";			
	
	public EmployeesDAOJdbc() {}

	public List<Employee> getEmployees(Integer firstResult, Integer countOfResults) {
		List<Employee> employees = new ArrayList<Employee>();
		ConnectionPool connectionPool = null;
		Connection connection = null;
		ResultSet employeesResult = null;
		ResultSet positionsResult = null;
		try {
			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.getConnection();
			PreparedStatement getEmployeesStatement =
					connection.prepareStatement(GET_EMPL_SQL);
			getEmployeesStatement.setInt(1, firstResult + countOfResults);
			getEmployeesStatement.setInt(2, firstResult);
			
			employeesResult = getEmployeesStatement.executeQuery();

			StringBuilder idString = new StringBuilder();
			while (employeesResult.next()) {
				String currId = String.valueOf(employeesResult.getLong(EMPLOYEE_ID_COLUMN));
				idString.append(currId).append(", ");
				Employee currEmp = buildEmployee(employeesResult);
				employees.add(currEmp);
			}
			idString = idString.deleteCharAt(idString.length() - 1);
			idString = idString.deleteCharAt(idString.length() - 1);
			String getPositionsSQL = GET_POS_SQL.replace("?", idString);
			Statement getPositionsStatement = connection.createStatement();
			employeesResult.close();

			positionsResult = getPositionsStatement.executeQuery(getPositionsSQL);

			Iterator<Employee> iter = employees.iterator();
			while (positionsResult.next()) {
				Long employeeId = positionsResult.getLong(EMPLOYEE_ID_COLUMN);
				Employee currEmployee = iter.next();
				Set<Position> positions = new HashSet<Position>();
				while (employeeId == currEmployee.getId()) {
					Position currPosition = buildPosition(positionsResult, currEmployee);
					positions.add(currPosition);
					if (!positionsResult.next()) {
						break;
					}
					employeeId = positionsResult.getLong(EMPLOYEE_ID_COLUMN);
				}
				currEmployee.setPositions(positions);
			}

			return employees;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (CommandException e) {
			e.printStackTrace();
		} finally {
			try {
				positionsResult.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				connectionPool.freeConnection(connection);
			} catch (CommandException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	private static Employee buildEmployee(ResultSet result) throws SQLException {
		String employeeAdressCountryName = result.getString(EMPLOYEE_ADRESS_COUNTRY_COLUMN);
		Country employeeAdressCountry = new Country(employeeAdressCountryName);
		String employeeAdressCityName = result.getString(EMPLOYEE_ADRESS_CITY_COLUMN);
		City employeeAdressCity = new City(employeeAdressCityName, employeeAdressCountry);
		String employeeAdressContent = result.getString(EMPLOYEE_ADRESS_COLUMN);
		Adress employeeAdress = new Adress(employeeAdressContent, employeeAdressCity);
		String firstName = result.getString(FIRST_NAME_COLUMN);
		String lastName = result.getString(LAST_NAME_COLUMN);
		Employee currEmployee = new Employee(firstName, lastName, employeeAdress);
		Long id = result.getLong(EMPLOYEE_ID_COLUMN);
		currEmployee.setId(id);
		return currEmployee;
	}
	
	private static Position buildPosition(ResultSet result, Employee currEmployee) throws SQLException {
		// Build office
		String officeAdressCountryName = result.getString(COUNTRY_COLUMN);
		Country officeAdressCountry = new Country(officeAdressCountryName);
		String officeAdressCityName = result.getString(CITY_COLUMN);
		City officeAdressCity = new City(officeAdressCityName, officeAdressCountry);
		String officeAdressContent = result.getString(OFFICE_ADRESS_COLUMN);
		Adress officeAdress = new Adress(officeAdressContent, officeAdressCity);
		String officeDescription = result.getString(OFFICE_DESCRIPTION_COLUMN);
		String companyName = result.getString(COMPANY_NAME_COLUMN);
		Company company = new Company(companyName);
		Office office = new Office(officeDescription, officeAdress, company);
		int countOfEmployees = result.getInt(COUNT_OF_EMPLOYEES_COLUMN);
		office.setCountOfEmployees(countOfEmployees);
		
		// Build position
		String positionName = result.getString(POSITION_NAME_COLUMN);
		Position position = new Position();
		position.setName(positionName);
		position.setOffice(office);
		
		return position;
	}

	public List<Employee> getEmployees() {
		List<Employee> employees = new ArrayList<Employee>();
		return employees;
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
