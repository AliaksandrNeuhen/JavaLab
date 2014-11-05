package com.epam.xmltransforming.exception;

public class XMLDAOException extends ServletException {
	private static final long serialVersionUID = 1l;
	
	public XMLDAOException() {
		super();
	}
	
	public XMLDAOException(String message) {
		super(message);
	}
	
	public XMLDAOException(String message, Exception e) {
		super(message, e);
	}
}
