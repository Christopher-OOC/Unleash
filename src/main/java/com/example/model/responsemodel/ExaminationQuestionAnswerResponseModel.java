package com.example.model.responsemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExaminationQuestionAnswerResponseModel {

	private int answerId;
	
	private String question;
	
	private List<ExaminationQuestionOptionResponseModel> examOptions = new ArrayList<>();
	
	private boolean isAttempted;

	@Override
	public int hashCode() {
		return Objects.hash(answerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationQuestionAnswerResponseModel other = (ExaminationQuestionAnswerResponseModel) obj;
		return answerId == other.answerId;
	}
	
	
}
