package com.example.model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="questions")
public class Question {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String questionId;
	
	@Lob
	@Column(columnDefinition="MEDIUMBLOB")
	private String question;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinTable(name="all_available_questions", joinColumns=@JoinColumn(name="question_id"),
		inverseJoinColumns=@JoinColumn(name="course_id"))
	private Course course;
	
	@OneToMany(mappedBy="question", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<QuestionOption> options = new ArrayList<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	} 
	
	

}
