package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.exceptions.NoResourceFoundException;
import com.example.model.dto.CourseDto;
import com.example.model.dto.InstructorDto;
import com.example.model.dto.QuestionDto;
import com.example.model.entity.Course;
import com.example.model.entity.Instructor;
import com.example.model.entity.Question;
import com.example.model.entity.Role;
import com.example.model.entity.User;
import com.example.model.error.ResourceNotFoundType;
import com.example.repository.CourseRepository;
import com.example.repository.InstructorRepository;
import com.example.repository.QuestionRepository;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.utilities.PinEncoderUtils;
import com.example.utilities.PublicIdGeneratorUtils;
import com.example.utilities.Roles;
import com.example.utilities.TokenGenerators;

@Service
public class InstructorServiceImpl implements InstructorService {

	private CourseRepository courseRepository;

	private InstructorRepository instructorRepository;
	
	private QuestionRepository questionRepository;
	
	private RoleRepository roleRepository;
		
	private BCryptPasswordEncoder passwordEncoder;

	private ModelMapper modelMapper;
	
	
	private Map<String, Object> propertyMap = Map.of("course_code", "courseCode", "course_name", "courseName",
			"date_created", "dateCreated");


	public InstructorServiceImpl(CourseRepository courseRepository, InstructorRepository instructorRepository,
			QuestionRepository questionRepository, RoleRepository roleRepository,
			UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, 
			ModelMapper modelMapper) {
		super();
		this.courseRepository = courseRepository;
		this.instructorRepository = instructorRepository;
		this.questionRepository = questionRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
	}

	@Override
	public void save(InstructorDto instructorDto) {
		Instructor instructor = modelMapper.map(instructorDto, Instructor.class);
		instructor.setInstructorId(PublicIdGeneratorUtils.generateId(30));
		
		User userInstructor = new User();
		userInstructor.setEmail(instructorDto.getEmail());
		userInstructor.setUserId(instructor.getInstructorId());
		userInstructor.setEmailVerificationStatus(true);
		userInstructor.setEmailVerificationToken(TokenGenerators.generateEmailVerificationToken(instructorDto.getEmail()));
		userInstructor.setPassword(passwordEncoder.encode(instructorDto.getPassword()));
		userInstructor.setPasswordResetToken(null);
		userInstructor.setPin(PinEncoderUtils.encodePin(instructorDto.getPin()));
		
		Optional<Role> optionalRole = roleRepository.findByName(Roles.ROLE_INSTRUCTOR.name());
		
		if (optionalRole.isPresent()) {
			userInstructor.setRoles(Arrays.asList(optionalRole.get()));
		}
		

		instructor.setUser(userInstructor);
		
		instructorRepository.save(instructor);
	}

	@Override
	public Page<CourseDto> getAllInstructorCourses(String instructorId, int page, int size, String sortFields) {

		Instructor instructor = checkIfInstructorExists(instructorId);

		// Search String will be the Instructor full name
		String search = instructor.getFullName();

		Sort sort = Sort.unsorted();

		String[] arrSortFields = sortFields.split(",");

		for (String sortField : arrSortFields) {
			String actualField = sortField.replace("-", "");

			if (sortField.startsWith("-")) {
				sort = sort.and(Sort.by((String) propertyMap.get(actualField)).descending());
			} else {
				sort = sort.and(Sort.by((String) propertyMap.get(actualField)).ascending());
			}
		}

		Pageable pageable = PageRequest.of(page - 1, size, sort);

		Page<Course> coursePage = courseRepository.getAllCourses(pageable, search);
		System.out.println(coursePage.getContent());

		if (coursePage.getContent().isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_COURSE);
		}
		
		java.lang.reflect.Type typeToken = new TypeToken<List<CourseDto>>() {}.getType();
		List<CourseDto> listDto = modelMapper.map(coursePage.getContent(), typeToken);

		return new PageImpl<>(listDto, pageable, coursePage.getTotalElements());
	}

	private Instructor checkIfInstructorExists(String instructorId) {
		Optional<Instructor> optional = instructorRepository.findByInstructorId(instructorId);

		if (optional.isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_INSTRUCTOR);
		}

		return optional.get();
	}
	
	@Override
	public QuestionDto addANewQuestionForACourse(QuestionDto questionDto, String courseId, String instructorId) {
		
		Instructor instructor = checkIfInstructorExists(instructorId);
		
		Course course = checkIfCourseExists(courseId);
		
		if (!instructor.getCoursesTaken().contains(course)) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_COURSE);
		}
		
		Question question = modelMapper.map(questionDto, Question.class);
		question.setQuestionId(PublicIdGeneratorUtils.generateId(30));
		
		question.getOptions().forEach(option -> {
			option.setQuestion(question);
		});
		
		question.setCourse(course);

		Question savedQuestion = questionRepository.save(question);
		
		return modelMapper.map(savedQuestion, QuestionDto.class);
	}

	@Override
	public QuestionDto updateAQuestionForACourse(QuestionDto questionDto, String courseId, String instructorId, String questionId) {
		
		Instructor instructor = checkIfInstructorExists(instructorId);
		
		Course course = checkIfCourseExists(courseId);
		
		if (!instructor.getCoursesTaken().contains(course)) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_COURSE);
		}
		
		Question formalQuestion = checkIfQuestionExists(questionId);
		
		Question updatedQuestion = modelMapper.map(questionDto, Question.class);
		updatedQuestion.setId(formalQuestion.getId());
		updatedQuestion.setQuestionId(questionId);
		
		updatedQuestion.getOptions().forEach(option -> {
			option.setQuestion(updatedQuestion);
		});
		
		
		Question savedQuestion = questionRepository.save(updatedQuestion);
		
		return modelMapper.map(savedQuestion, QuestionDto.class);
	}
	
	private Question checkIfQuestionExists(String questionId) {
		Optional<Question> optional = questionRepository.findByQuestionId(questionId);
		
		if (optional.isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_QUESTION);
		}
		
		return optional.get();
	}

	private Course checkIfCourseExists(String courseId) {
		Optional<Course> optional = courseRepository.findByCourseId(courseId);
		
		if (optional.isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_COURSE);
		}
		
		return optional.get();
	}

	@Override
	public InstructorDto getInstructorByEmail(String email) {
		Optional<Instructor> optional = instructorRepository.findByEmail(email);
		
		if (optional.isEmpty()) {
			throw new InstructorNotFoundException(email);
		}
		
		Instructor instructor = optional.get();
		
		return modelMapper.map(instructor, InstructorDto.class);
	}
}
