package com.example.model.responsemodel;

import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.example.model.entity.EnrolledCourseId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Relation(collectionRelation="enrolled_courses")
public class EnrolledCourseResponseModel extends RepresentationModel<CourseResponseModel> {

	private EnrolledCourseId enrolledCourseId = new EnrolledCourseId();

	private StudentResponseModel student;

	private CourseResponseModel course;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date enrolledOn;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnrolledCourseResponseModel other = (EnrolledCourseResponseModel) obj;
		return Objects.equals(enrolledCourseId, other.enrolledCourseId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(enrolledCourseId);
		return result;
	}
}
