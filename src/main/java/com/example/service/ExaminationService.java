package com.example.service;


import com.example.model.dto.ExaminationDto;
import com.example.model.dto.ExaminationQuestionAnswerDto;
import com.example.model.dto.ExaminationResultDto;

public interface ExaminationService {
	
	ExaminationDto startExamination(String courseId, String studentId);
	
	ExaminationDto endExamination(String courseId, String studentId);
	
	ExaminationResultDto checkResult(int sessionId, String studentId);
	
	ExaminationQuestionAnswerDto getNextQuestion(int sessionId, String studentId);
	
	ExaminationDto submitPrevoiusQuestion(ExaminationDto examDto);
	
}
