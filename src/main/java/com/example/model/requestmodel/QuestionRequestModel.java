package com.example.model.requestmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionRequestModel {
	
	@NotEmpty(message="There must be value for question")
	private String question;
	
	@Size(min=4, max=4, message="The number of question must be exartly four (4)")
	private List<QuestionOptionRequestModel> options = new ArrayList<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionRequestModel other = (QuestionRequestModel) obj;
		return Objects.equals(options, other.options) && Objects.equals(question, other.question);
	}

	@Override
	public int hashCode() {
		return Objects.hash(options, question);
	}
	
	
}
