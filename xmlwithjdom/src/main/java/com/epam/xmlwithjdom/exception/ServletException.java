package com.epam.xmlwithjdom.exception;

/**
 * Main project exception class
 * 
 */

public class ServletException extends Exception {
	private static final long serialVersionUID = 1l;
	
	public ServletException() {
		super();
	}
	
	public ServletException(String message) {
		super(message);
	}
	
	public ServletException(String message, Exception e) {
		super(message, e);
	}
}
