package com.example.exceptions;

public class AlreadyEnrolledForTheCourseException extends RuntimeException {

	private static final long serialVersionUID = 2420515263908134042L;
	
	public AlreadyEnrolledForTheCourseException(String courseId) {
		super("You have already enrolled for the course with ID: " + courseId);
	}

}
