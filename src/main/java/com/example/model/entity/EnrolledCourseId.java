package com.example.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class EnrolledCourseId implements Serializable {
	
	private static final long serialVersionUID = 5366195477813058939L;
	
	@Column(name="student_id")
	private int studentId;
	
	@Column(name="course_id")
	private int courseId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnrolledCourseId other = (EnrolledCourseId) obj;
		return courseId == other.courseId && studentId == other.studentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseId, studentId);
	}
}
