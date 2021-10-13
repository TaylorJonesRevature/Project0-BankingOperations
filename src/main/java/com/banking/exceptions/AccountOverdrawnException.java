package com.banking.exceptions;

public class AccountOverdrawnException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AccountOverdrawnException() {
		super("Your account does not have the funds to cover this transaction.");
	}
}
