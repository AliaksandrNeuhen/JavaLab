package com.epam.employees.command;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.employees.dao.EmployeesDAO;
import com.epam.employees.dao.implementation.EmployeesDAOHibernate;
import com.epam.employees.dao.implementation.EmployeesDAOJdbc;
import com.epam.employees.dao.implementation.EmployeesDAOJpa;
import com.epam.employees.entity.Employee;
import com.epam.employees.exception.CommandException;

/**
 * Command class for showing list of employees with pagination
 *
 */
public final class ShowEmployeesCommand implements ICommand {
	
	// Request parameter names
	private static final String FIRST_RESULT_REQUEST_PARAM = "first";
	private static final String MAX_RESULTS_REQUEST_PARAM = "max";
	private static final String EMPLOYEES_REQUEST_ATTR = "employees";
	private static final String PREV_MAX_REQUEST_PARAM = "prev_max";
	private static final String ACTION_REQUEST_PARAM = "action";
	
	private static final String NEXT_ACTION = "next";
	private static final String PREV_ACTION = "prev";

	/**
	 * Execute command for showing employees list
	 * @param request - current servlet request
	 * @param response - current servlet response
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException {
		EmployeesDAO dao = new EmployeesDAOHibernate();

		// Number of first employee to show
		int firstResult = Integer.valueOf(request.getParameter(FIRST_RESULT_REQUEST_PARAM));
		// Count of results to show on page
		int countOfResults = Integer.valueOf(request.getParameter(MAX_RESULTS_REQUEST_PARAM));

		// Count of results in previous request.
		// It is needed for correct displaying of count
		int prevCountOfResults = 0;
		if (request.getParameter(PREV_MAX_REQUEST_PARAM) != null) {
			prevCountOfResults = Integer.valueOf(request.getParameter(PREV_MAX_REQUEST_PARAM));	
		}

		// Elements needed to fetch depend on pagination choice
		String action = request.getParameter(ACTION_REQUEST_PARAM);
		if (NEXT_ACTION.equals(action)) {
			firstResult += prevCountOfResults;
		} else if (PREV_ACTION.equals(action)) {
			firstResult -= prevCountOfResults;
			if (firstResult < 0) {
				firstResult = 0;
			}
		}

		// Get list of employees
		List<Employee> employees = dao.getEmployees(firstResult, countOfResults);

		request.setAttribute(PREV_MAX_REQUEST_PARAM, countOfResults);
		request.setAttribute(FIRST_RESULT_REQUEST_PARAM, firstResult);
		request.setAttribute(MAX_RESULTS_REQUEST_PARAM, countOfResults);
		request.setAttribute(EMPLOYEES_REQUEST_ATTR, employees);
	}
}