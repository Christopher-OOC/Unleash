package com.example.exceptions;

public class NoOngoingExaminationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoOngoingExaminationException(String studentId) {
		super("No ongoing examination for student with ID: " + studentId);
	}
}
