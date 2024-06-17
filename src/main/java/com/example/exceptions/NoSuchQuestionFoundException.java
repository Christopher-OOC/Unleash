package com.example.exceptions;

public class NoSuchQuestionFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchQuestionFoundException(String questionId) {
		super("No such question found with question ID: " + questionId);
	}
}
