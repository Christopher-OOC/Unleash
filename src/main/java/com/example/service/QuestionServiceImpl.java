package com.example.service;

import java.util.List;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.exceptions.NoQuestionAvailableForTheCourseException;
import com.example.exceptions.NoSuchCourseFoundException;
import com.example.exceptions.NoSuchQuestionFoundException;
import com.example.model.entity.Course;
import com.example.model.entity.Question;
import com.example.repository.CourseRepository;
import com.example.repository.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	private CourseRepository courseRepository;
	
	private QuestionRepository questionRepository;
	
	private ModelMapper modelMapper;
	
	

	public QuestionServiceImpl(CourseRepository courseRepository, QuestionRepository questionRepository,
			ModelMapper modelMapper) {
		super();
		this.courseRepository = courseRepository;
		this.questionRepository = questionRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public Question getQuestionById(String questionId) {
		return checkIfQuestionExists(questionId);
	}

	private Question checkIfQuestionExists(String questionId) {
		Optional<Question> optional = questionRepository.findByQuestionId(questionId);
		
		if (optional.isEmpty()) {
			throw new NoSuchQuestionFoundException(questionId);
		}
		
		return optional.get();
	}

	@Override
	public List<Question> getAllAvailableQuestionForACourse(String courseId) {
		checkIfCourseExists(courseId);
		
		return checkIfAnyQuestion(courseId);
	}

	private List<Question> checkIfAnyQuestion(String courseId) {
		List<Question> questions = questionRepository.getAllAvailableQuestionForACourse(courseId);
		
		if (questions.isEmpty()) {
			throw new NoQuestionAvailableForTheCourseException(courseId);
		}
		
		return questions;
	}
	
	private Course checkIfCourseExists(String courseId) {
		Optional<Course> optional = courseRepository.findByCourseId(courseId);
		
		if (optional.isEmpty()) {
			throw new NoSuchCourseFoundException("" + courseId);
		}
		
		return optional.get();
	}

}
