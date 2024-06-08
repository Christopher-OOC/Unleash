package com.example.model.dto;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ExaminationSessionDto {
	
	@JsonProperty("examination_session_id")
	private int examinationSessionId;
	
	@JsonProperty("start_time")
	@FutureOrPresent(message="The start time cannot be in the past")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date startTime;
	
	@JsonProperty("end_time")
	@Future(message="The end time must be in the future")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date endTime;
	
	@JsonProperty("session_closed")
	private boolean sessionClosed;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationSessionDto other = (ExaminationSessionDto) obj;
		return examinationSessionId == other.examinationSessionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(examinationSessionId);
	}

	
}
