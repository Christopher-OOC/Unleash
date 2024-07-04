package com.example.exceptions;

public class NoResourceFoundException extends RuntimeException {

	private static final long serialVersionUID = 4151864165437768759L;
	
	public NoResourceFoundException(String message) {
		super(message);
	}

}
