package com.example.exceptions;

public class ExaminationSessionAlreadyCreated extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExaminationSessionAlreadyCreated(int courseId) {
		super("Examination Session has already created for course ID: " + courseId);
	}
}
