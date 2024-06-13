package com.example.model.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class ExaminationId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="examination_session_id")
	private ExaminationSession sessionId;
	
	@OneToOne
	@JoinColumn(name="student_id")
	private Student studentId;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExaminationId other = (ExaminationId) obj;
		return Objects.equals(sessionId, other.sessionId) && Objects.equals(studentId, other.studentId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sessionId, studentId);
	}
	
	

}
