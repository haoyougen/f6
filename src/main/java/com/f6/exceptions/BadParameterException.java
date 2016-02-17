package com.f6.exceptions;

public class BadParameterException extends Exception {
	private static final long serialVersionUID = 1L;

	public BadParameterException() {
		super();
	}

	public BadParameterException(String message) {
		super(message);
	}

	public BadParameterException(Throwable cause) {
		super(cause);
	}

	public BadParameterException(Exception cause) {
		super(cause);
	}

	public BadParameterException(String message, Throwable cause) {
		super(message, cause);
	}
}
