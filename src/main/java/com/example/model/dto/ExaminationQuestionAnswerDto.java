package com.example.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.model.Examination;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExaminationQuestionAnswerDto {
	
	private int answerId;
	
	private String question;
	
	@JsonIgnore
	private Examination examinationId;
	
	private List<ExaminationQuestionOptionDto> examOptions = new ArrayList<>();
	
	private boolean isAttempted;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationQuestionAnswerDto other = (ExaminationQuestionAnswerDto) obj;
		return answerId == other.answerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(answerId);
	}
	
	

}
