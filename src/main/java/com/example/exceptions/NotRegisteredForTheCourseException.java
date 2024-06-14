package com.example.exceptions;

public class NotRegisteredForTheCourseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotRegisteredForTheCourseException(String courseId) {
		super("You don't  offer the course with ID: " + courseId + ". Please enroll for the course!");
	}
}
