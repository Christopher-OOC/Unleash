package com.example.model.requestmodel;

import java.util.Objects;


import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseRequestModel {
	
	@NotNull(message="Course code cannot be null")
	@NotBlank(message="Course code cannot be empty")
	@Length(min=4, max=10, message="Course code must have between 4-10 characters")
	private String courseCode;
	
	@NotNull(message="Course name cannot be null")
	@NotBlank(message="Course namee cannot be empty")
	@Length(max=100, message="Course name must have 100 characters or less")
	private String courseName;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseRequestModel other = (CourseRequestModel) obj;
		return Objects.equals(courseCode, other.courseCode) && Objects.equals(courseName, other.courseName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseCode, courseName);
	}

	@Override
	public String toString() {
		return "CourseRequestModel [courseCode=" + courseCode + ", courseName=" + courseName + "]";
	}

}
