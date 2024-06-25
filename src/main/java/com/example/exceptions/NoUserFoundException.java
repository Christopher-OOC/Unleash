package com.example.exceptions;

public class NoUserFoundException extends RuntimeException {

	private static final long serialVersionUID = 2679879386853409523L;
	
	public NoUserFoundException(String email) {
		super(email);
	}

}
