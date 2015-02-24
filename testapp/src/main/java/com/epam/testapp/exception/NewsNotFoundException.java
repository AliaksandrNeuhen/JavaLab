package com.epam.testapp.exception;

public class NewsNotFoundException extends ProjectException {
	private static final long serialVersionUID = 1L;

	public NewsNotFoundException() {
	}

	public NewsNotFoundException(String message) {
		super(message);
	}

	public NewsNotFoundException(String message, Throwable exception) {
		super(message, exception);
	}
}
