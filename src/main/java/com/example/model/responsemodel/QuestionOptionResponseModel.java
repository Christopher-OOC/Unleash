package com.example.model.responsemodel;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"optionId", "optionValue", "isCorrect"})
public class QuestionOptionResponseModel {
	
	@JsonProperty("option_id")
	private long optionId;
	
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
		QuestionOptionResponseModel other = (QuestionOptionResponseModel) obj;
		return optionId == other.optionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(optionId);
	}
	
}
