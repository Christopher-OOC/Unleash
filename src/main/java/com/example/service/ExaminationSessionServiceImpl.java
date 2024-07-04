package com.example.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.ExaminationSessionAlreadyCreated;
import com.example.exceptions.NoResourceFoundException;
import com.example.model.dto.ExaminationSessionDto;
import com.example.model.entity.Course;
import com.example.model.entity.ExaminationSession;
import com.example.model.entity.Instructor;
import com.example.model.error.ResourceNotFoundType;
import com.example.repository.CourseRepository;
import com.example.repository.ExaminationSessionRepository;
import com.example.repository.InstructorRepository;

@Service
public class ExaminationSessionServiceImpl implements ExaminationSessionService {
	
	private CourseRepository courseRepository;
	
	private ExaminationSessionRepository examinationSessionRepository;
	
	private InstructorRepository instructorRepository;

	public ExaminationSessionServiceImpl(CourseRepository courseRepository,
			InstructorRepository instructorRepository,
			ExaminationSessionRepository examinationSessionRepository) {
		super();
		this.courseRepository = courseRepository;
		this.examinationSessionRepository = examinationSessionRepository;
		this.instructorRepository = instructorRepository;
	}

	@Override
	public void createExamSessionForACourse(String instructorId, String courseId, ExaminationSessionDto dto) {
		
		Instructor instructor = checkIfInstructorExists(instructorId);
		
		Course course = checkIfCourseExists(courseId);
		
		if (!instructor.getCoursesTaken().contains(course)) {
			throw new BadRequestException("You cannot perform this operation because you don't teach this course");
		}
		
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
	
	private Instructor checkIfInstructorExists(String instructorId) {
		Optional<Instructor> optional = instructorRepository.findByInstructorId(instructorId);
		
		if (optional.isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_INSTRUCTOR);
		}
		
		return optional.get();
	}

	private Course checkIfCourseExists(String courseId) {
		Optional<Course> optional = courseRepository.findByCourseId(courseId);
		
		if (optional.isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_COURSE);
		}
		
		Course course = optional.get();
		
		return course;
	}

	@Override
	public void closeExamSessionForACourse(String instructorId, String courseId) {
		
		Instructor instructor = checkIfInstructorExists(instructorId);
		
		Course course = checkIfCourseExists(courseId);
		
		if (!instructor.getCoursesTaken().contains(course)) {
			throw new BadRequestException("You cannot perform this operation because you don't teach this course");
		}
		
		ExaminationSession currentSession = examinationSessionRepository.findCurrentExaminationSession(courseId);
		
		if (currentSession == null) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_SESSION);
		}
		
		currentSession.setSessionClosed(true);
		
		examinationSessionRepository.save(currentSession);
	}

	@Override
	public ExaminationSessionDto getCurrentExaminationSession(String courseId) {
		
		
		return null;
	}

}
