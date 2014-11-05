package com.epam.xmltransforming.command;

import javax.servlet.http.HttpServletRequest;

import com.epam.xmltransforming.exception.CommandException;

public class ErrorCommand implements ICommand {

	public String execute(HttpServletRequest request) throws CommandException {
		
		return "error.jsp";
	}

}
