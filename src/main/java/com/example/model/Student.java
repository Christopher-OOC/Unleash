package com.example.model;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="students")
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int studentId;
	
	@Column(length=20)
	private String firstName;
	
	@Column(length=20)
	private String lastName;
	
	@Column(length=20)
	private String middleName;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column(unique=true)
	private String email;
	
	private String password;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="course_student_enrolled", joinColumns=@JoinColumn(name="student_id"),
			inverseJoinColumns=@JoinColumn(name="course_id"))
	private List<Course> coursesTaken = new ArrayList<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return studentId == other.studentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId);
	}

	
}
