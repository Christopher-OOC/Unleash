package com.example.service;


import com.example.model.Examination;
import com.example.model.dto.ExaminationDto;
import com.example.model.dto.ExaminationResultDto;

public interface ExaminationService {
	
	ExaminationDto startExamination(int courseId, int studentId);
	
	Examination endExamination(int courseId, int studentId);
	
	ExaminationResultDto checkResult(int sessionId, int studentId);
	
	ExaminationDto submitPrevoiusAndGetNextQuestion(ExaminationDto examDto);
	
}
