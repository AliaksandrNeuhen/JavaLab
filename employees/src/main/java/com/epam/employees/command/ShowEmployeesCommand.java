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

public final class ShowEmployeesCommand implements ICommand {
	
	// Request parameter names
	private static final String FIRST_RESULT_REQUEST_PARAM = "first";
	private static final String MAX_RESULTS_REQUEST_PARAM = "max";
	private static final String EMPLOYEES_REQUEST_ATTR = "employees";
	private static final String PREV_MAX_REQUEST_PARAM = "prev_max";
	private static final String ACTION_REQUEST_PARAM = "action";
	
	private static final String NEXT_ACTION = "next";
	private static final String PREV_ACTION = "prev";

	
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException {
		EmployeesDAO dao = new EmployeesDAOJdbc();

		int firstResult = Integer.valueOf(request.getParameter(FIRST_RESULT_REQUEST_PARAM));
		int countOfResults = Integer.valueOf(request.getParameter(MAX_RESULTS_REQUEST_PARAM));
		int prevCountOfResults = 0;
		if (request.getParameter(PREV_MAX_REQUEST_PARAM) != null) {
			prevCountOfResults = Integer.valueOf(request.getParameter(PREV_MAX_REQUEST_PARAM));	
		}
		String action = request.getParameter(ACTION_REQUEST_PARAM);
		if (NEXT_ACTION.equals(action)) {
			firstResult += prevCountOfResults;
		} else if (PREV_ACTION.equals(action)) {
			firstResult -= prevCountOfResults;
			if (firstResult < 0) {
				firstResult = 0;
			}
		}
		List<Employee> employees = null; 
		employees = dao.getEmployees(firstResult, countOfResults);
		request.setAttribute(PREV_MAX_REQUEST_PARAM, countOfResults);
		request.setAttribute(FIRST_RESULT_REQUEST_PARAM, firstResult);
		request.setAttribute(MAX_RESULTS_REQUEST_PARAM, countOfResults);
		request.setAttribute(EMPLOYEES_REQUEST_ATTR, employees);
	}
}