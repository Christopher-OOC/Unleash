package com.example.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseDto {
	
	private int courseId;
	
	private String courseCode;
	
	private String courseName;
	
	private InstructorDto instructor;
	
	private List<StudentDto> studentEnrolled = new ArrayList<>();
	
	private List<QuestionDto> allAvailableQuestions = new ArrayList<>();
	
	private Date dateCreated;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseDto other = (CourseDto) obj;
		return courseId == other.courseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(courseId);
		return result;
	}

	@Override
	public String toString() {
		return "CourseDto [courseId=" + courseId + ", courseCode=" + courseCode + ", courseName=" + courseName
				+ ", dateCreated=" + dateCreated + "]";
	}	
}
