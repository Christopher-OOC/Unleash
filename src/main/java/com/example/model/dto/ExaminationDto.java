package com.example.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.example.model.entity.ExaminationId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExaminationDto {
	
	private ExaminationId examinationId = new ExaminationId();

	private List<ExaminationQuestionAnswerDto> examinationQuestions = new ArrayList<>();
	
	private ExaminationQuestionAnswerDto previousQuestion;
	
	private Date startTime;

	private Date endTime;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationDto other = (ExaminationDto) obj;
		return Objects.equals(examinationId, other.examinationId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(examinationId);
		return result;
	}
}
