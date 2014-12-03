package com.epam.xmlwithjdom.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.xmlwithjdom.exception.CommandException;

/**
 * Command for returning to previous page
 * 
 */

public final class BackCommand implements ICommand {
	private static final String PAGE_REQUEST_PARAM = "page";
	private static final String COMMAND_NAME_SHOW = "SHOW_";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException {
		String backPage = request.getParameter(PAGE_REQUEST_PARAM);
		StringBuilder commandName = new StringBuilder();
		commandName.append(COMMAND_NAME_SHOW);
		commandName.append(backPage.toUpperCase());
		ICommand command = CommandHelper.getInstance().getCommand(commandName.toString());
		command.execute(request, response);
	}

}
