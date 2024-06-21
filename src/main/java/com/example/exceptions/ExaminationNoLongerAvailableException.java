package com.example.exceptions;

public class ExaminationNoLongerAvailableException extends RuntimeException {

	private static final long serialVersionUID = 3020882180653215096L;
	
	public ExaminationNoLongerAvailableException() {
		super("You cannot write this exam again because you have written it before");
	}

}
