package com.epam.xmlwithjdom.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.xmlwithjdom.exception.CommandException;

/**
 * Interface for implementing commands that process http requests
 */

public interface ICommand {
	
	/**
	 * Executes the command.
	 * @param request - http request to the server
	 * @param response - http response from the server
	 * @throws CommandException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) 
			throws CommandException;
	
}
