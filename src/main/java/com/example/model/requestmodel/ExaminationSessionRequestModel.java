package com.example.model.requestmodel;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExaminationSessionRequestModel {
	
	@FutureOrPresent(message="The start time cannot be in the past")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date startTime;
	
	@Future(message="The end time must be in the future")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date endTime;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationSessionRequestModel other = (ExaminationSessionRequestModel) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(startTime, other.startTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(endTime, startTime);
	}
	
	
}
