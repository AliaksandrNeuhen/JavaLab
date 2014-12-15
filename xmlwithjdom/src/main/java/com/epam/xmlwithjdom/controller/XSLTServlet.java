package com.epam.xmlwithjdom.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.xmlwithjdom.command.CommandHelper;
import com.epam.xmlwithjdom.command.ICommand;
import com.epam.xmlwithjdom.exception.CommandException;

/**
 * Main servlet class
 * 
 */

public final class XSLTServlet extends HttpServlet {
	private static final long serialVersionUID = 1l;
	
	private static final String COMMAND_NAME = "command";
	private static final String ERROR_PAGE = "error.html";
	
	public XSLTServlet() {
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
		String commandName = request.getParameter(COMMAND_NAME);
		ICommand command = CommandHelper.getInstance().getCommand(commandName);
		try {
			command.execute(request, response);
		} catch (CommandException e) {
			e.printStackTrace();
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}
}
