package com.example.model.responsemodel;

import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"courseId", "courseCode", "courseName", "numberOfStudentEnrolled", "dateCreated", "instructor"})
@Relation(collectionRelation="courses")
public class CourseResponseModel extends RepresentationModel<CourseResponseModel> {
	
	private String courseId;
	
	private String courseCode;
	
	private String courseName;
	
	private int numberOfStudentEnrolled;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date dateCreated;
	
	private InstructorResponseModel instructor;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseResponseModel other = (CourseResponseModel) obj;
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
		return "CourseResponseModel [courseId=" + courseId + ", courseCode=" + courseCode + ", courseName=" + courseName
				+ ", dateCreated=" + dateCreated + "]";
	}

	

}
