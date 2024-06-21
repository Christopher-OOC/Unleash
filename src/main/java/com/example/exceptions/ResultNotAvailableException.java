package com.example.exceptions;

public class ResultNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResultNotAvailableException() {
		super("Your result is not available!!!. Keep checking");
	}
}
