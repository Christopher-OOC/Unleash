package com.example.model.requestmodel;


import java.util.Date;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentRequestModel {
	
	@NotNull(message="First name cannot be null")
	@NotBlank(message="First name cannot be empty")
	@Length(min=2, max=20, message="First name must have between 2-20 characters")
	private String firstName;
	
	@NotNull(message="Last name cannot be null")
	@NotBlank(message="Last name cannot be empty")
	@Length(min=2, max=20, message="Last name must have between 2-20 characters")
	private String lastName;
	
	@NotNull(message="Middle name cannot be null")
	@NotBlank(message="Middle name cannot be empty")
	@Length(min=2, max=20, message="Middle name must have between 2-20 characters")
	private String middleName;
	
	@NotNull(message="Email cannot be null")
	@NotBlank(message="Email cannot be empty")
	@Email(message="Email provided must be a valid email")
	private String email;
	
	@NotNull(message="Password cannot be null")
	@NotBlank(message="Password cannot be empty")
	@Length(min=8, max=20, message="Password must have between 8-20 characters")
	private String password;
	
	@NotNull(message="Confirm Password cannot be null")
	@NotBlank(message="Confirm Password cannot be empty")
	@Length(min=8, max=20, message="Password must have between 8-20 characters")
	private String confirmPassword;
	
	@Past(message="Date of birth must be in the past")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date dateOfBirth;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRequestModel other = (StudentRequestModel) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

}
