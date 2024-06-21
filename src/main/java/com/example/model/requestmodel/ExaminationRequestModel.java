package com.example.model.requestmodel;

import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"sessionId", "studentId", "nextQuestion", "startTime", "endTime"})
public class ExaminationRequestModel extends RepresentationModel<ExaminationRequestModel> {
	
	@JsonProperty("examination_session_id")
	private int sessionId;
	
	@JsonProperty("student_id")
	private String studentId;
	
	@JsonProperty("examination_questions")
	private ExaminationQuestionAnswerRequestModel nextQuestion;
	
	@JsonProperty("start_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date startTime;
	
	@JsonProperty("end_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date endTime;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationRequestModel other = (ExaminationRequestModel) obj;
		return sessionId == other.sessionId && Objects.equals(studentId, other.studentId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(sessionId, studentId);
		return result;
	}
}
