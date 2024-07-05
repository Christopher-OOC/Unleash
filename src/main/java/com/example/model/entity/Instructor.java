package com.example.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="instructors")
public class Instructor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(length=30, nullable=false)
	private String instructorId;
	
	@Column(length=50)
	private String fullName;
	
	@Column(unique=true)
	private String email;
	
	@OneToMany(mappedBy="instructor", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Course> coursesTaken = new ArrayList<>();
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private User user;
	
	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	private Date dateRegistered;
	
	public Instructor instructorId(String id) {
		setInstructorId(id);
		
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
		Instructor other = (Instructor) obj;
		return instructorId == other.instructorId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(instructorId);
	}

	@Override
	public String toString() {
		return "Instructor [id=" + id + ", instructorId=" + instructorId + ", fullName=" + fullName + ", email=" + email
				+ ", dateRegistered=" + dateRegistered + "]";
	}
}
