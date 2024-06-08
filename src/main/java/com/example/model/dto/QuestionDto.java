package com.example.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"questionId", "question", "options"})
public class QuestionDto {
	
	@JsonProperty("question_id")
	private int questionId;
	
	private String question;
	
	private List<QuestionOptionDto> options = new ArrayList<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionDto other = (QuestionDto) obj;
		return questionId == other.questionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(questionId);
	}

	

}
