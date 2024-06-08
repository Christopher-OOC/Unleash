package com.example.model.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.example.model.ExaminationQuestionAnswer;
import com.example.model.Question;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"sessionId", "studentId", "nextQuestion", "startTime", "endTime"})
public class ExaminationDto extends RepresentationModel<ExaminationDto> {
	
	@JsonProperty("examination_session_id")
	private int sessionId;
	
	@JsonProperty("student_id")
	private int studentId;
	
	@JsonProperty("examination_questions")
	private ExaminationQuestionAnswerDto nextQuestion;
	
	@JsonProperty("start_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date startTime;
	
	@JsonProperty("end_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date endTime;

	@Override
	public int hashCode() {
		return Objects.hash(sessionId, studentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationDto other = (ExaminationDto) obj;
		return sessionId == other.sessionId && studentId == other.studentId;
	}
	
	

}
