package com.example.exceptions;

public class NoStudentAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoStudentAvailableException(String message) {
		super(message);
	}
}
