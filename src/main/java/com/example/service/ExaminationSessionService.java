package com.example.service;

import com.example.model.dto.ExaminationSessionDto;
import com.example.model.entity.ExaminationSession;

public interface ExaminationSessionService {
	
	void createExamSessionForACourse(String instructotId, String courseId, ExaminationSessionDto dto);
	
	ExaminationSessionDto getCurrentExaminationSession(String courseId);
	
	void closeExamSessionForACourse(String instructorId, String courseId);

}
