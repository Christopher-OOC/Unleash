package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	Optional<Question> findByQuestionId(String questionId);
	
	@Query("SELECT q FROM Question q WHERE q.course.courseId = ?1")
	List<Question> getAllAvailableQuestionForACourse(String courseId);
	
	@Query("""
			SELECT q FROM Question q WHERE q.course.courseId = ?1
			ORDER BY RAND() LIMIT ?2
			""")
	List<Question> getExaminationQuestions(String courseId, int numberOfExaminationQuestions);

}
