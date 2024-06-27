package com.example.exceptions;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1052526514146602011L;
	
	public ResourceNotFoundException() {
		super("No such resource found!");
	}

}
