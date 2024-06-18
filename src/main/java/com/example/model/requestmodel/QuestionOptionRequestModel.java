package com.example.model.requestmodel;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionOptionRequestModel {
	
	private long optionId;
	
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
		QuestionOptionRequestModel other = (QuestionOptionRequestModel) obj;
		return isCorrect == other.isCorrect && Objects.equals(optionValue, other.optionValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(isCorrect, optionValue);
	}
	
	
	
}
