package com.epam.testappview.exception;

public class FetchByIdException extends DaoException {
	private static final long serialVersionUID = 1L;

	public FetchByIdException() {
	}

	public FetchByIdException(String msg) {
		super(msg);
	}

	public FetchByIdException(String msg, Exception e) {
		super(msg, e);
	}
}
