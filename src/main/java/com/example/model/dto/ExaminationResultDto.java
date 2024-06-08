package com.example.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExaminationResultDto {
	
	private List<ExaminationQuestionAnswerDto> examinationQuestions = new ArrayList<>();
	
	private int numberOfCorrectAnswer;
	
	private int totalNumberOfExaminationQuestions;
	
	private String description;
	
	private Date startTime;
	
	private Date endTime;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationResultDto other = (ExaminationResultDto) obj;
		return Objects.equals(endTime, other.endTime) && numberOfCorrectAnswer == other.numberOfCorrectAnswer
				&& Objects.equals(startTime, other.startTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(endTime, numberOfCorrectAnswer, startTime);
	}

	
}
