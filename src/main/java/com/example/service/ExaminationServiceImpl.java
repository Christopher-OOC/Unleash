package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.example.exceptions.NoSuchCourseFoundException;
import com.example.exceptions.NoSuchStudentFoundException;
import com.example.exceptions.NotRegisteredForTheCourseException;
import com.example.controller.ExaminationApiController;
import com.example.exceptions.NoExaminationSessionException;
import com.example.exceptions.NoOngoingExaminationException;
import com.example.model.dto.ExaminationDto;
import com.example.model.dto.ExaminationQuestionAnswerDto;
import com.example.model.dto.ExaminationResultDto;
import com.example.model.entity.Course;
import com.example.model.entity.Examination;
import com.example.model.entity.ExaminationId;
import com.example.model.entity.ExaminationQuestionAnswer;
import com.example.model.entity.ExaminationQuestionOption;
import com.example.model.entity.ExaminationSession;
import com.example.model.entity.Question;
import com.example.model.entity.QuestionOption;
import com.example.model.entity.Student;
import com.example.repository.CourseRepository;
import com.example.repository.ExaminationQuestionAnswerRepository;
import com.example.repository.ExaminationRepository;
import com.example.repository.ExaminationSessionRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExaminationServiceImpl implements ExaminationService {

	private StudentRepository studentRepository;

	private CourseRepository courseRepository;

	private ExaminationSessionRepository sessionRepository;

	private QuestionRepository questionRepository;

	private ExaminationRepository examinationRepository;
	
	private ExaminationQuestionAnswerRepository questionAnswerRepository;

	private ModelMapper modelMapper;

	@Value("${examination.api.numberOfExaminationQuestion}")
	private int numberOfExaminationQuestion;

	public ExaminationServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
			ExaminationSessionRepository sessionRepository, QuestionRepository questionRepository,
			ExaminationRepository examinationRepository, ExaminationQuestionAnswerRepository questionAnswerRepository,
			 ModelMapper modelMapper) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.sessionRepository = sessionRepository;
		this.questionRepository = questionRepository;
		this.examinationRepository = examinationRepository;
		this.questionAnswerRepository = questionAnswerRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public ExaminationDto startExamination(String courseId, String studentId) {
		// Get Student
		Student student = checkIfStudentExist(studentId);

		// Get Course
		Course course = checkIfCourseExist(courseId);

		// Check if student register for the course
		if (!student.getCoursesTaken().contains(course)) {
			throw new NotRegisteredForTheCourseException(courseId);
		}

		// Get Current Exam Session
		ExaminationSession session = checkIfSessionExists(courseId);
		
		log.info("Session: {}", session);

		ExaminationId id = new ExaminationId();
		id.setSessionId(session);
		id.setStudentId(student);

		// Get List of Questions
		List<Question> questions = questionRepository.getExaminationQuestions(courseId, numberOfExaminationQuestion);

		// Create new Examination
		Examination newExamination = new Examination();
		newExamination.setExaminationId(id);
		newExamination.setStartTime(new Date());

		// Bind Question to ExaminationQuestionAnswer
		questions.forEach(question -> {
			ExaminationQuestionAnswer answer = questionToQuestionAnswer(question, newExamination);

			newExamination.getExaminationQuestions().add(answer);
		});
		
		//Save Examination
		Examination savedExamination = examinationRepository.save(newExamination);
		
		log.info("Saved Examintion: {}", savedExamination);
		
		//To ExaminationDto
		ExaminationDto examDto = modelMapper.map(savedExamination, ExaminationDto.class);
		
		var questionAnswer = questionAnswerRepository.findExaminationNextQuestion(session.getExaminationSessionId(), studentId);
		var questionAnswerDto = modelMapper.map(questionAnswer, ExaminationQuestionAnswerDto.class);
		
		examDto.setNextQuestion(questionAnswerDto);
		
		examDto.add(linkTo(methodOn(ExaminationApiController.class).submitPrevoiusAndGetNextQuestion(null)).withRel("next_question"));
		
		return examDto;
	}

	private ExaminationSession checkIfSessionExists(String courseId) {
		ExaminationSession session = sessionRepository.findCurrentExaminationSession(courseId);

		if (session == null) {
			throw new NoExaminationSessionException(courseId);
		}
		return session;
	}

	private Course checkIfCourseExist(String courseId) {
		Optional<Course> optionalCourse = courseRepository.findByCourseId(courseId);

		if (optionalCourse.isEmpty()) {
			throw new NoSuchCourseFoundException("" + courseId);
		}
		
		return optionalCourse.get();
	}

	private Student checkIfStudentExist(String studentId) {
		Optional<Student> optionalStudent = studentRepository.findByStudentId(studentId);

		if (optionalStudent.isEmpty()) {
			throw new NoSuchStudentFoundException(studentId);
		}

	    return optionalStudent.get();
	}

	@Override
	public Examination endExamination(String courseId, String studentId) {
		checkIfCourseExist(courseId);

		// Get Current Exam Session
		ExaminationSession session = checkIfSessionExists(courseId);

		// Get Student
		Student student = checkIfStudentExist(studentId);

		// Get Examination
		ExaminationId id = new ExaminationId();
		id.setSessionId(session);
		id.setStudentId(student);

		Examination examination = checkIfExaminationOngoing(studentId, id);

		// Set the end time
		examination.setEndTime(new Date());

		return examinationRepository.save(examination);
	}

	private Examination checkIfExaminationOngoing(String studentId, ExaminationId id) {
		Optional<Examination> optionalExamination = examinationRepository.findById(id);

		if (optionalExamination.isEmpty()) {
			throw new NoOngoingExaminationException(studentId);
		}

		Examination examination = optionalExamination.get();
		return examination;
	}

	@Override
	public ExaminationResultDto checkResult(int sessionId, String studentId) {
		// Get Session
		ExaminationSession session = checkIfSessionExists("" + studentId);

		// Get Student
		Student student = checkIfStudentExist(studentId);

		// Find The Particular Exam
		ExaminationId examId = new ExaminationId();
		examId.setSessionId(session);
		examId.setStudentId(student);

		// Get the Required Examination
		Examination exam = checkIfExaminationOngoing(studentId, examId);

		// Bind Examination to ExaminationResultDto
		ExaminationResultDto resultDto = modelMapper.map(exam, ExaminationResultDto.class);

		int totalNumberOfQuestions = exam.getExaminationQuestions().size();
		resultDto.setTotalNumberOfExaminationQuestions(totalNumberOfQuestions);

		int correctAnswer = getNumberOfExaminationCorrectAnswers(exam);
		resultDto.setNumberOfCorrectAnswer(correctAnswer);

		String description = generateExamDescption(totalNumberOfQuestions, correctAnswer);
		resultDto.setDescription(description);

		return resultDto;
	}
	
	@Override
	public ExaminationDto submitPrevoiusAndGetNextQuestion(ExaminationDto examDto) {
		Examination exam = modelMapper.map(examDto, Examination.class);
		
		var questionAnswerDto = examDto.getNextQuestion();
		
		var questionAnswer = modelMapper.map(questionAnswerDto, ExaminationQuestionAnswer.class);
		questionAnswer.setExaminationId(exam);
		questionAnswer.setAttempted(true);
		
		questionAnswerRepository.save(questionAnswer);
		
		var nextQuestion = questionAnswerRepository.findExaminationNextQuestion(examDto.getSessionId(), examDto.getStudentId());
		var nextQuestionDto = modelMapper.map(nextQuestion, ExaminationQuestionAnswerDto.class);
		
		examDto.setNextQuestion(nextQuestionDto);
		examDto.removeLinks();
		examDto.add(linkTo(methodOn(ExaminationApiController.class).submitPrevoiusAndGetNextQuestion(null)).withRel("next_question"));
		
		return examDto;
	}

	private String generateExamDescption(int totalNumberOfQuestions, int correctAnswer) {
		double percentage = (correctAnswer * 1.0) / totalNumberOfQuestions;

		if (percentage >= 0.7) {
			return "An excellent result!. You scored " + correctAnswer + " out of " + totalNumberOfQuestions
					+ ". Keep it up!";
		} else if (percentage >= 0.5 && percentage < 0.7) {
			return "An average result!. You scored " + correctAnswer + " out of " + totalNumberOfQuestions
					+ ". With constant study and practice you can do better!";
		}
		else {
			return "A poor result!. You scored " + correctAnswer + " out of " + totalNumberOfQuestions
					+ ". Never lose hope, with constant study and practice you can do better!";
		}
	}

	private int getNumberOfExaminationCorrectAnswers(Examination exam) {
		var examinationQuestions = exam.getExaminationQuestions();
		int numberOfCorrectAnswer = 0;

		for (var question : examinationQuestions) {
			for (var option : question.getExamOptions()) {
				if (option.isChosen() && option.isCorrect()) {
					numberOfCorrectAnswer++;
				}
			}
		}

		return numberOfCorrectAnswer;
	}

	private ExaminationQuestionAnswer questionToQuestionAnswer(Question question, Examination exam) {
		ExaminationQuestionAnswer answer = new ExaminationQuestionAnswer();

		answer.setExaminationId(exam);
		answer.setQuestion(question.getQuestion());

		question.getOptions().forEach(option -> {
			ExaminationQuestionOption answerOption = questionOptionToAnswerOption(option, answer);
			answer.getExamOptions().add(answerOption);
		});

		return answer;
	}

	private ExaminationQuestionOption questionOptionToAnswerOption(QuestionOption option,
			ExaminationQuestionAnswer answer) {
		ExaminationQuestionOption answerOption = new ExaminationQuestionOption();

		answerOption.setAnswerId(answer);
		answerOption.setCorrect(option.isCorrect());
		answerOption.setOptionValue(option.getOptionValue());

		return answerOption;
	}

}
