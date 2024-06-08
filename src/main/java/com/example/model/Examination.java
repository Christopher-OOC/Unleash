package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="examinations")
public class Examination {
	
	@EmbeddedId
	private ExaminationId examinationId = new ExaminationId();
	
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="examination_questions", joinColumns= {@JoinColumn(name="examination_session_id"), @JoinColumn(name="student_id")}, 
		inverseJoinColumns=@JoinColumn(name="examination_question_id"))
	private List<ExaminationQuestionAnswer> examinationQuestions = new ArrayList<>();
	
	@CreationTimestamp
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
		Examination other = (Examination) obj;
		return Objects.equals(examinationId, other.examinationId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(examinationId);
	}

	@Override
	public String toString() {
		return "Examination [examinationId=" + examinationId + ", startTime=" + startTime + ", endTime=" + endTime
				+ "]";
	}
	
	

}