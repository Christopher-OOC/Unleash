package com.example.model.responsemodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExaminationResultResponseModel {
	
	
	private List<ExaminationQuestionAnswerResponseModel> examinationQuestions = new ArrayList<>();
	
	private int numberOfCorrectAnswer;
	
	private int totalNumberOfExaminationQuestions;
	
	private String description;
	
	private Date startTime;
	
	private Date endTime;

	@Override
	public int hashCode() {
		return Objects.hash(endTime, numberOfCorrectAnswer, startTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationResultResponseModel other = (ExaminationResultResponseModel) obj;
		return Objects.equals(endTime, other.endTime) && numberOfCorrectAnswer == other.numberOfCorrectAnswer
				&& Objects.equals(startTime, other.startTime);
	}
}
