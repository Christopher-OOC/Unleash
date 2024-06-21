package com.example.model.responsemodel;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"sessionId", "studentId", "nextQuestion", "startTime", "endTime"})
public class ExaminationResponseModel {


	private int sessionId;
	
	private String studentId;

	private ExaminationQuestionAnswerResponseModel nextQuestion;
	
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
		ExaminationResponseModel other = (ExaminationResponseModel) obj;
		return sessionId == other.sessionId && Objects.equals(studentId, other.studentId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sessionId, studentId);
	}
}
