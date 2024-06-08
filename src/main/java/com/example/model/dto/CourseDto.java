package com.example.model.dto;

import java.util.Date;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"courseId", "courseCode", "courseName", "instructorId", "dateCreated"})
@Relation(collectionRelation="courses")
public class CourseDto extends RepresentationModel<CourseDto> {
	
	@JsonProperty("course_id")
	private int courseId;
	
	@JsonProperty("course_code")
	@NotNull(message="Course code cannot be null")
	@NotBlank(message="Course code cannot be empty")
	@Length(min=4, max=10, message="Course code must have between 4-10 characters")
	private String courseCode;
	
	@JsonProperty("course_name")
	@NotNull(message="Course name cannot be null")
	@NotBlank(message="Course namee cannot be empty")
	@Length(max=100, message="Course name must have 100 characters or less")
	private String courseName;
	
	@JsonProperty("instructor_id")
	private int instructorId;
	
	@JsonProperty("date_created")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
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
		return instructorId == other.instructorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(instructorId);
	}

	
}
