package com.example.model.requestmodel;
import java.util.Date;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"fullName", "email", "password", "dateRegistered"})
public class InstructorRequestModel {
	
	@NotNull(message="Full name cannot be null")
	@NotBlank(message="Full name cannot be empty")
	@Length(min=5, max=50, message="Full name must have between 5-50 characters")
	private String fullName;
	
	@Past(message="Date of birth must be in the past")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date dateOfBirth;
	
	@NotNull(message="Email cannot be null")
	@NotBlank(message="Email cannot be empty")
	@Email(message="Email provided must be a valid email")
	private String email;
	
	@NotNull(message="Password cannot be null")
	@NotBlank(message="Password cannot be empty")
	@Length(min=8, max=20, message="Password must have between 8-20 characters")
	private String password;
	
	@NotNull(message="Confirm password cannot be null")
	@NotBlank(message="Confirm password cannot be empty")
	@Length(min=8, max=20, message="Confirm password must have between 8-20 characters")
	private String confirmPassword;
	
	@NotBlank(message="Pin cannot be empty")
	private String pin;
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstructorRequestModel other = (InstructorRequestModel) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public String toString() {
		return "InstructorRequestModel [fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", confirmPassword=" + confirmPassword + "]";
	}

}
