package com.epam.employees.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.employees.command.ICommand;
import com.epam.employees.command.ShowEmployeesCommand;
import com.epam.employees.exception.CommandException;

/**
 * Main servlet class
 * 
 */

public final class EmployeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1l;
	
	private static final String EMPLOYEES_JSP = "employees.jsp";
	private static final String ERROR_PAGE = "error.html";
	
	public EmployeesServlet() {
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		proccessRequest(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		proccessRequest(request, response);
	}
	
	protected void proccessRequest(HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException{
		ICommand command = new ShowEmployeesCommand();
		try {
			command.execute(request, response);
			request.getRequestDispatcher(EMPLOYEES_JSP).forward(request, response);
		} catch (CommandException e) {
			e.printStackTrace();
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}
}
