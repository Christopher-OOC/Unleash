package com.example.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.exceptions.NoQuestionAvailableForTheCourseException;
import com.example.exceptions.NoSuchCourseFoundException;
import com.example.exceptions.NoSuchQuestionFoundException;
import com.example.model.dto.QuestionDto;
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
	public Question getQuestionById(int questionId) {
		return checkIfQuestionExists(questionId);
	}

	private Question checkIfQuestionExists(int questionId) {
		Optional<Question> optional = questionRepository.findById(questionId);
		
		if (optional.isEmpty()) {
			throw new NoSuchQuestionFoundException(questionId);
		}
		
		return optional.get();
	}

	@Override
	public List<Question> getAllAvailableQuestionForACourse(int courseId) {
		checkIfCourseExists(courseId);
		
		return checkIfAnyQuestion(courseId);
	}

	private List<Question> checkIfAnyQuestion(int courseId) {
		List<Question> questions = questionRepository.getAllAvailableQuestionForACourse(courseId);
		
		if (questions.isEmpty()) {
			throw new NoQuestionAvailableForTheCourseException(courseId);
		}
		
		return questions;
	}

	private Course checkIfCourseExists(int courseId) {
		Optional<Course> optional = courseRepository.findById(courseId);
		
		if (optional.isEmpty()) {
			throw new NoSuchCourseFoundException("" + courseId);
		}
		
		return optional.get();
	}

	@Override
	public Question addANewQuestionForACourse(QuestionDto questionDto, int courseId) {
		
		Course course = checkIfCourseExists(courseId);
		
		Question question = modelMapper.map(questionDto, Question.class);
		
		question.getOptions().forEach(option -> {
			option.setQuestion(question);
		});
		
		question.setCourse(course);

		Question savedQuestion = questionRepository.save(question);
		
		return savedQuestion;
	}

	@Override
	public Question updateAQuestionForACourse(QuestionDto questionDto, int questionId) {
		
		checkIfQuestionExists(questionId);
		
		Question updatedQuestion = modelMapper.map(questionDto, Question.class);
		updatedQuestion.setQuestionId(questionId);
		
		updatedQuestion.getOptions().forEach(option -> {
			option.setQuestion(updatedQuestion);
		});
		
		return questionRepository.save(updatedQuestion);
	}

}
