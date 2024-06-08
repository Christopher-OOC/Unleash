package com.example.exceptions;

public class NoCourseAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoCourseAvailableException(String message) {
		super(message);
	}
}
