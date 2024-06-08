package com.example.model.dto;

import java.util.Date;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"studentId", "firstName", "lastName", "middleName", "email", "password", "dateOfBirth"})
public class StudentDto {
	
	@JsonProperty("student_id")
	private int studentId;
	
	@NotNull(message="First name cannot be null")
	@NotBlank(message="First name cannot be empty")
	@Length(min=2, max=20, message="First name must have between 2-20 characters")
	@JsonProperty("first_name")
	private String firstName;
	
	@NotNull(message="Last name cannot be null")
	@NotBlank(message="Last name cannot be empty")
	@Length(min=2, max=20, message="Last name must have between 2-20 characters")
	@JsonProperty("last_name")
	private String lastName;
	
	@NotNull(message="Middle name cannot be null")
	@NotBlank(message="Middle name cannot be empty")
	@Length(min=2, max=20, message="Middle name must have between 2-20 characters")
	@JsonProperty("middle_name")
	private String middleName;
	
	@NotNull(message="Email cannot be null")
	@NotBlank(message="Email cannot be empty")
	@Email(message="Email provided must be a valid email")
	private String email;
	
	@NotNull(message="Password cannot be null")
	@NotBlank(message="Password cannot be empty")
	@Length(min=8, max=20, message="Password must have between 8-20 characters")
	private String password;
	
	@Past(message="Date of birth must be in the past")
	@JsonProperty("date_of_birth")
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
		StudentDto other = (StudentDto) obj;
		return studentId == other.studentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId);
	}
	
}
