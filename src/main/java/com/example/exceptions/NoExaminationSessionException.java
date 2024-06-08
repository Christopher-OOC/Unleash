package com.example.exceptions;

public class NoExaminationSessionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoExaminationSessionException(int courseId) {
		super("No examination session for course ID: " + courseId);
	}
}
