package com.example.model.dto;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"instructorId", "fullName", "email", "password", "dateRegistered"})
public class InstructorDto {
	
	@JsonProperty("instructor_id")
	private int instructorId;
	
	@NotNull(message="Last name cannot be null")
	@NotBlank(message="Last name cannot be empty")
	@Length(min=2, max=20, message="Last name must have between 2-20 characters")
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
	
	@JsonProperty("date_registered")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date dateRegistered;

}
