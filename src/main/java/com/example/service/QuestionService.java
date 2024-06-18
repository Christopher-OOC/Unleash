package com.example.service;

import java.util.List;

import com.example.model.entity.Question;

public interface QuestionService {
	
	Question getQuestionById(String questionId);
	
	List<Question> getAllAvailableQuestionForACourse(String courseId);
	

}
