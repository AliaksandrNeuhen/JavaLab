package com.epam.xmltransforming.command;

import javax.servlet.http.HttpServletRequest;

import com.epam.xmltransforming.exception.CommandException;

public interface ICommand {
	
	public String execute(HttpServletRequest request) throws CommandException;
	
}
