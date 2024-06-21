package com.example.service;

import com.example.model.dto.ExaminationSessionDto;
import com.example.model.entity.ExaminationSession;

public interface ExaminationSessionService {
	
	void createExamSessionForACourse(String courseId, ExaminationSessionDto dto);
	
	ExaminationSessionDto getCurrentExaminationSession(String courseId);
	
	ExaminationSession closeExamSessionForACourse(int courseId);

}
