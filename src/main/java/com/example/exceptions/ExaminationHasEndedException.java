package com.example.exceptions;

public class ExaminationHasEndedException extends RuntimeException {

	private static final long serialVersionUID = -4366916221411929024L;
	
	public ExaminationHasEndedException(String message) {
		super(message);
	}

}
