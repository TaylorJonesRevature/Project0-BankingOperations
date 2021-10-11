package com.banking.exceptions;

public class LoginFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginFailedException() {
		super("Your login attempt has failed.");
	}
}
