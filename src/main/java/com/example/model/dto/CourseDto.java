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
	
	private int id;
	
	private String courseId;
	
	private String courseCode;
	
	private String courseName;
	
	private InstructorDto instructor;
	
	private List<EnrolledCourseDto> studentEnrolled = new ArrayList<>();
	
	private List<QuestionDto> allAvailableQuestions = new ArrayList<>();
	
	private Date dateCreated;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseDto other = (CourseDto) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "CourseDto [id=" + id + ", courseId=" + courseId + ", courseCode=" + courseCode + ", courseName="
				+ courseName + ", dateCreated=" + dateCreated + "]";
	}
}
