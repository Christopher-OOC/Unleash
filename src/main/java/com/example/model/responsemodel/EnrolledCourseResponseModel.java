package com.example.model.responsemodel;

import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.example.model.entity.EnrolledCourseId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrolledCourseResponseModel extends RepresentationModel<CourseResponseModel> {

	private EnrolledCourseId id = new EnrolledCourseId();

	private StudentResponseModel student;

	private CourseResponseModel course;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date enrolledOn;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnrolledCourseResponseModel other = (EnrolledCourseResponseModel) obj;
		return Objects.equals(id, other.id);
	}
}
