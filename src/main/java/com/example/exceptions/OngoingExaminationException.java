package com.example.exceptions;

public class OngoingExaminationException extends RuntimeException {

	private static final long serialVersionUID = -3841847228838421826L;
	
	public OngoingExaminationException(String message) {
		super(message);
	}

}
