package com.banking.exceptions;

public class UserDoesNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public UserDoesNotExistException() {
		super("Your username or password are incorrect. Try again.");
	}
}
