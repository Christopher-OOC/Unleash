package com.example.exceptions;


public class NoSuchStudentFoundException extends  RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchStudentFoundException(String studentId) {
		super("No student found with the id: " + studentId);
	}
}
