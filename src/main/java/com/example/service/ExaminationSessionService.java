package com.example.service;

import com.example.model.ExaminationSession;
import com.example.model.dto.ExaminationSessionDto;

public interface ExaminationSessionService {
	
	ExaminationSession createExamSessionForACourse(ExaminationSessionDto dto, int courseId);
	
	
	ExaminationSession closeExamSessionForACourse(int courseId);

}
