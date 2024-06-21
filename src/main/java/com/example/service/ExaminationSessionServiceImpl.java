package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.exceptions.ExaminationSessionAlreadyCreated;
import com.example.exceptions.NoSuchCourseFoundException;
import com.example.model.dto.ExaminationSessionDto;
import com.example.model.entity.Course;
import com.example.model.entity.ExaminationSession;
import com.example.repository.CourseRepository;
import com.example.repository.ExaminationSessionRepository;

@Service
public class ExaminationSessionServiceImpl implements ExaminationSessionService {
	
	private CourseRepository courseRepository;
	
	private ExaminationSessionRepository examinationSessionRepository;
	
	

	public ExaminationSessionServiceImpl(CourseRepository courseRepository, ExaminationSessionRepository examinationSessionRepository) {
		super();
		this.courseRepository = courseRepository;
		this.examinationSessionRepository = examinationSessionRepository;
	}

	@Override
	public void createExamSessionForACourse(String courseId, ExaminationSessionDto dto) {
		
		Course course = checkIfCourseExists(courseId);
		
		// check if no current exam session
		
		ExaminationSession currentSession = examinationSessionRepository.findCurrentExaminationSession(courseId);
		
		if (currentSession == null) {
			currentSession = new ExaminationSession();
			currentSession.setCourse(course);
			currentSession.setSessionClosed(false);
			currentSession.setStartTime(dto.getStartTime());
			currentSession.setEndTime(dto.getEndTime());
			
			examinationSessionRepository.save(currentSession);
		}
		else {
			throw new ExaminationSessionAlreadyCreated(courseId);
		}
		
	}

	private Course checkIfCourseExists(String courseId) {
		Optional<Course> optional = courseRepository.findByCourseId(courseId);
		
		if (optional.isEmpty()) {
			throw new NoSuchCourseFoundException("" + courseId);
		}
		
		Course course = optional.get();
		
		return course;
	}

	@Override
	public ExaminationSession closeExamSessionForACourse(int courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExaminationSessionDto getCurrentExaminationSession(String courseId) {
		
		
		return null;
	}

}
