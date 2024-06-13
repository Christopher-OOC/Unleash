package com.example.model.requestmodel;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"fullName", "email", "password", "dateRegistered"})
public class InstructorRequestModel {
	
	@NotNull(message="Full name cannot be null")
	@NotBlank(message="Full name cannot be empty")
	@Length(min=5, max=50, message="Last name must have between 5-50 characters")
	@JsonProperty("full_name")
	private String fullName;
	
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
