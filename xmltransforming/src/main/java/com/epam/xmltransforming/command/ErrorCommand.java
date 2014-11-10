package com.epam.xmltransforming.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.xmltransforming.exception.CommandException;

public class ErrorCommand implements ICommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws CommandException {
	}

}
