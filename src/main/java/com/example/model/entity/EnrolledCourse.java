package com.example.model.entity;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="enrolled_courses")
public class EnrolledCourse {
	
	@EmbeddedId
	private EnrolledCourseId id = new EnrolledCourseId();
	
	@ManyToOne
	@JoinColumn(name="student_id", insertable=false, updatable=false)
	private Student student;
	
	@JoinColumn(name="course_id", insertable=false, updatable=false)
	@ManyToOne
	private Course course;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date enrolledOn;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnrolledCourse other = (EnrolledCourse) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
