package com.example.model.responsemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"questionId", "question", "options"})
public class QuestionResponseModel {
	
	private String questionId;
	
	private String question;
	
	private List<QuestionOptionResponseModel> options = new ArrayList<>();

	@Override
	public int hashCode() {
		return Objects.hash(questionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionResponseModel other = (QuestionResponseModel) obj;
		return Objects.equals(questionId, other.questionId);
	}
	
	

}
