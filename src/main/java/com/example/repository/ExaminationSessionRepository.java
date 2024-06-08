package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.ExaminationSession;

public interface ExaminationSessionRepository extends JpaRepository<ExaminationSession, Integer> {
	
	@Query("SELECT e FROM ExaminationSession e WHERE e.course.courseId = ?1 AND e.sessionClosed = false")
	ExaminationSession findCurrentExaminationSession(int courseId);
	
	@Query("SELECT e FROM ExaminationSession e WHERE e.examinationSessionId = ?1")
	ExaminationSession findAnyExaminationSession(int sessionId);
}
