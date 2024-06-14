package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.entity.ExaminationQuestionAnswer;

public interface ExaminationQuestionAnswerRepository extends JpaRepository<ExaminationQuestionAnswer, Integer> {
	
	@Query("""
			SELECT q FROM ExaminationQuestionAnswer q 
			WHERE q.examinationId.examinationId.sessionId.examinationSessionId = ?1
			AND
			q.examinationId.examinationId.studentId.studentId = ?2
			AND
			q.isAttempted = false
			ORDER BY RAND()
			LIMIT 1
			""")
	ExaminationQuestionAnswer findExaminationNextQuestion(int sessionId, String studentId);
}
