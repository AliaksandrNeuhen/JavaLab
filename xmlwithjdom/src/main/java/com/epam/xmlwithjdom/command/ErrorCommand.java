package com.epam.xmlwithjdom.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.xmlwithjdom.exception.CommandException;


public final class ErrorCommand implements ICommand {

	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws CommandException {
	}

}
