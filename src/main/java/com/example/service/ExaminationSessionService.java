package com.example.service;

import com.example.model.dto.ExaminationSessionDto;
import com.example.model.entity.ExaminationSession;

public interface ExaminationSessionService {
	
	ExaminationSession createExamSessionForACourse(ExaminationSessionDto dto, int courseId);
	
	
	ExaminationSession closeExamSessionForACourse(int courseId);

}
