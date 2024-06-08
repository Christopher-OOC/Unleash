package com.example.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="examination_session")
public class ExaminationSession {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int examinationSessionId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="courseId")
	private Course course;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
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
		ExaminationSession other = (ExaminationSession) obj;
		return examinationSessionId == other.examinationSessionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(examinationSessionId);
	}

	@Override
	public String toString() {
		return "ExaminationSession [examinationSessionId=" + examinationSessionId + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", sessionClosed=" + sessionClosed + "]";
	}

}
