package com.epam.xmltransforming.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.xmltransforming.command.CommandHelper;
import com.epam.xmltransforming.command.ICommand;
import com.epam.xmltransforming.exception.CommandException;

public class XMLParserServlet extends HttpServlet {
	private static final long serialVersionUID = 1l;
	private static final Logger log = Logger.getLogger(XMLParserServlet.class);
	
	private static final String COMMAND_NAME = "command";
	private static final String ERROR_PAGE = "error.html";
	
	public XMLParserServlet() {
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		String page = "";
		String commandName = request.getParameter(COMMAND_NAME);
		ICommand command = CommandHelper.getInstance().getCommand(commandName);
		
		try {
			page = command.execute(request);
		} catch (CommandException e) {
			log.error(e.getMessage(), e);
			page = ERROR_PAGE;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			page = ERROR_PAGE;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		} else {
			response.setContentType("text/html");
			response.getWriter().write("Error!");
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = "";
		String commandName = request.getParameter(COMMAND_NAME);
		ICommand command = CommandHelper.getInstance().getCommand(commandName);
		
		try {
			page = command.execute(request);
		} catch (CommandException e) {
			log.error(e.getMessage(), e);
			page = ERROR_PAGE;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			page = ERROR_PAGE;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		if (dispatcher != null) {
			dispatcher.forward(request, response);
		} else {
			response.setContentType("text/html");
			response.getWriter().write("Error!");
		}
	}
}
