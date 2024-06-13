package com.example.model.entity;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="examination_question_answers")
public class ExaminationQuestionAnswer {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int answerId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns(value= {@JoinColumn(name="examination_session_id"), @JoinColumn(name="student_id")})
	private Examination examinationId;
	
	private String question;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="answer_id")
	private List<ExaminationQuestionOption> examOptions = new ArrayList<>();
	
	private boolean isAttempted;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationQuestionAnswer other = (ExaminationQuestionAnswer) obj;
		return answerId == other.answerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(answerId);
	}

	
	
}
