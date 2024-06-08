package com.example.exceptions;

public class NoSuchInstructorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchInstructorException(int instructorId) {
		super("No such instructor with ID: " + instructorId);
	}
}
