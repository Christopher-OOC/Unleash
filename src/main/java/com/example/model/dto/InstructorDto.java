package com.example.model.dto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.model.entity.Course;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"instructorId", "fullName", "email", "password", "dateRegistered"})
public class InstructorDto {
	
	private int id;
	
	private String instructorId;
	
	private String fullName;
	
	private String email;
	
	private String password;
	
	private List<Course> coursesTaken = new ArrayList<>();
	
	private Date dateRegistered;

}
