package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	@Query("SELECT q FROM Question q WHERE q.course.courseId = ?1")
	List<Question> getAllAvailableQuestionForACourse(int courseId);
	
	@Query("""
			SELECT q FROM Question q WHERE q.course.courseId = ?1
			ORDER BY RAND() LIMIT ?2
			""")
	List<Question> getExaminationQuestions(String courseId, int numberOfExaminationQuestions);

}
