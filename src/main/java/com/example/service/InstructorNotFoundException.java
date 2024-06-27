package com.example.service;

public class InstructorNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5918296275107675767L;

	public InstructorNotFoundException(String email) {
		super("No instructor found with email: " + email);
	}
	
	

}
