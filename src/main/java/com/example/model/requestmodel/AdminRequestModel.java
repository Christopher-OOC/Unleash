package com.example.model.requestmodel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminRequestModel {

	private String email;

	private String firstName;

	private String lastName;

	private String middleName;

	private String password;
	
	private String confirmPassword;

	private String pin;

}
