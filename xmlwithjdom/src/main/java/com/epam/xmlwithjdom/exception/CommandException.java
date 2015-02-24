package com.epam.xmlwithjdom.exception;

/**
 * Exception class using for detecting errors in ICommand-implementing classes
 * 
 */
public class CommandException extends ServletException{
	private static final long serialVersionUID = 1l;
	
	public CommandException() {
		super();
	}
	
	public CommandException(String message) {
		super(message);
	}
	
	public CommandException(String message, Exception e){
		super(message, e);
	}
}
