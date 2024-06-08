package com.example.exceptions;

public class NoExaminationFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoExaminationFoundException(int studentId) {
		super("No examination found for student with ID: " + studentId);
	}
}
