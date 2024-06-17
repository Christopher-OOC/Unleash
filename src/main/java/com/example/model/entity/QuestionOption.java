package com.example.model.entity;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="question_options")
public class QuestionOption {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long optionId;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="question_id")
	private Question question;
	
	private String optionValue;
	
	private boolean isCorrect;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionOption other = (QuestionOption) obj;
		return optionId == other.optionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(optionId);
	}
	
}
