package com.banking.exceptions;

public class DuplicateUsernameException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DuplicateUsernameException() {
		super("Please choose a different username.");
	}

}
