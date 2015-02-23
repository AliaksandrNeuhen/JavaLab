package com.epam.testappservice.exception;

/**
 * Exception thrown when there are some troubles with application
 */

public class ProjectException extends Exception {
	private static final long serialVersionUID = 1L;

	public ProjectException() {
	}

	public ProjectException(String message) {
		super(message);
	}

	public ProjectException(String message, Throwable exception) {
		super(message, exception);
	}
}
