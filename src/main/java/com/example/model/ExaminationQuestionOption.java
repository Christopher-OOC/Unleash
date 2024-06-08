package com.example.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="examination_question_options")
public class ExaminationQuestionOption {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int answerOptionId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinTable(name="examination_answer_options", joinColumns= @JoinColumn(name="answer_option_id"))
	private ExaminationQuestionAnswer answerId;
	
	private String optionValue;
	
	private boolean isCorrect;
	
	private boolean chosen;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationQuestionOption other = (ExaminationQuestionOption) obj;
		return Objects.equals(answerId, other.answerId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(answerId);
	}

	
	
	

}
