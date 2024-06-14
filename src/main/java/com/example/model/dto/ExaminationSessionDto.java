package com.example.model.dto;

import java.util.Date;
import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ExaminationSessionDto {
	
	private int examinationSessionId;
	
	private Date startTime;
	
	private Date endTime;
	
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
