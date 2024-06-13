package com.example.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=30, nullable=false)
	private String courseId;
	
	@Column(length=10, unique=true)
	private String courseCode;
	
	@Column(length=100, unique=true)
	private String courseName;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="instuctor_id")
	private Instructor instructor;
	
	@ManyToMany(mappedBy="coursesTaken", fetch=FetchType.EAGER)
	private List<Student> studentEnrolled = new ArrayList<>();
	
	@OneToMany(mappedBy="course")
	private List<Question> allAvailableQuestions = new ArrayList<>();
	
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	private Date dateCreated;
	
	public Course getInstanceWithId(String id) {
		Instructor instuctor = new Instructor();
		instuctor.setInstructorId(id);
		
		this.setInstructor(instuctor);
		
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return courseId == other.courseId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(courseId);
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseCode=" + courseCode + ", courseName=" + courseName
				+ ", studentEnrolled=" + studentEnrolled + ", dateCreated=" + dateCreated + "]";
	}
	
	

}
