package com.example.model.dto;

import java.util.Objects;

import com.example.model.entity.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionOptionDto {

	@JsonProperty("option_id")
	private int optionId;
	
	@JsonIgnore
	private Question question;
	
	@JsonProperty("option_value")
	private String optionValue;
	
	@JsonProperty("is_correct")
	private boolean isCorrect;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionOptionDto other = (QuestionOptionDto) obj;
		return optionId == other.optionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(optionId);
	}
}
