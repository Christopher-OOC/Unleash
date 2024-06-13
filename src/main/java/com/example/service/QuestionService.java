package com.example.service;

import java.util.List;

import com.example.model.dto.QuestionDto;
import com.example.model.entity.Question;

public interface QuestionService {
	
	Question getQuestionById(int questionId);
	
	List<Question> getAllAvailableQuestionForACourse(int courseId);
	
	Question addANewQuestionForACourse(QuestionDto questionDto, int courseId);
	
	Question updateAQuestionForACourse(QuestionDto questionDto,int questionId);

}
