package com.epam.xmltransforming.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.xmltransforming.exception.CommandException;

public class BackCommand implements ICommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws CommandException {
		String backPage = request.getParameter("page");
		StringBuilder commandName = new StringBuilder();
		commandName.append("SHOW_");
		commandName.append(backPage.toUpperCase());
		ICommand command = CommandHelper.getInstance().getCommand(commandName.toString());
		command.execute(request, response);
	}

}
