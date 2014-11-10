package com.epam.xmltransforming.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.xmltransforming.command.CommandHelper;
import com.epam.xmltransforming.command.ICommand;
import com.epam.xmltransforming.exception.CommandException;

public final class XSLTServlet extends HttpServlet {
	private static final long serialVersionUID = 1l;
	private static final Logger log = Logger.getLogger(XSLTServlet.class);
	
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
			log.error(e.getMessage(), e);
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}
}
