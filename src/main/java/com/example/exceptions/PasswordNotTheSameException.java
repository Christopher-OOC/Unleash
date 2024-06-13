package com.example.exceptions;

public class PasswordNotTheSameException extends RuntimeException {

	private static final long serialVersionUID = 2212950756256193902L;

	public PasswordNotTheSameException() {
		super("Password not the same");
	}

}
