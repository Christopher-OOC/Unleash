package com.example.exceptions;

public class ExaminationCompletionException extends RuntimeException {
	
	private static final long serialVersionUID = 48080846891125416L;

	public ExaminationCompletionException() {
		super("You have completed your examination!!!");
	}
	
}
