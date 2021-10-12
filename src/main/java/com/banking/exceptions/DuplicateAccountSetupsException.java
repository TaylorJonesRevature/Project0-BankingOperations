package com.banking.exceptions;

public class DuplicateAccountSetupsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateAccountSetupsException() {
		super("Multiole account setup attempts.");
	}
}
