package com.example.service;

import java.util.List;

import com.example.model.dto.QuestionDto;
import com.example.model.entity.Question;

public interface QuestionService {
	
	Question getQuestionById(String questionId);
	
	List<Question> getAllAvailableQuestionForACourse(String courseId);
	
	QuestionDto addANewQuestionForACourse(QuestionDto questionDto, String courseId);
	
	QuestionDto updateAQuestionForACourse(QuestionDto questionDto, String questionId);

}
