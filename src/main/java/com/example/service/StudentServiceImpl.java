package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Arrays;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.exceptions.AlreadyEnrolledForTheCourseException;
import com.example.exceptions.NoResourceFoundException;
import com.example.exceptions.ResourceNotFoundException;
import com.example.model.dto.CourseDto;
import com.example.model.dto.EnrolledCourseDto;
import com.example.model.dto.StudentDto;
import com.example.model.entity.Course;
import com.example.model.entity.EnrolledCourse;
import com.example.model.entity.EnrolledCourseId;
import com.example.model.entity.Role;
import com.example.model.entity.Student;
import com.example.model.entity.User;
import com.example.model.entity.UserType;
import com.example.model.error.ResourceNotFoundType;
import com.example.repository.CourseRepository;
import com.example.repository.EnrolledCourseRepository;
import com.example.repository.RoleRepository;
import com.example.repository.StudentRepository;
import com.example.repository.UserRepository;
import com.example.utilities.PinEncoderUtils;
import com.example.utilities.PublicIdGeneratorUtils;
import com.example.utilities.Roles;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;

	private CourseRepository courseRepository;
	
	private EnrolledCourseRepository enrolledCourseRepository;
	
	private RoleRepository roleRepository;
	
	private UserRepository userRepository;
	
	private BCryptPasswordEncoder passwordEncoder;

	private ModelMapper modelMapper;

	private Map<String, String> propertyStudent = Map.of("first_name", "firstName", "last_name", "lastName", "middle_name",
			"middleName");
	
	private Map<String, String> propertyCourse = Map.of("course_name", "courseName", "course_code", "courseCode");

	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
			RoleRepository roleRepository, EnrolledCourseRepository enrolledCourseRepository,
			UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, 
			ModelMapper modelMapper) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.enrolledCourseRepository = enrolledCourseRepository;
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
	}

	@Override
	public void register(StudentDto dto) {
		
		Student student = modelMapper.map(dto, Student.class);
		dto.setStudentId(PublicIdGeneratorUtils.generateId(30));
		
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setEmailVerificationStatus(false);
		user.setEmailVerificationToken(null);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setPasswordResetToken(null);
		user.setPin(PinEncoderUtils.encodePin(dto.getPin()));
		
		Optional<Role> optionalRole = roleRepository.findByName(Roles.ROLE_STUDENT.name());
		
		if (optionalRole.isPresent()) {
			user.setRoles(Arrays.asList(optionalRole.get()));
		}
		
		student.setUser(user);

		studentRepository.save(student);
	}

	@Override
	public StudentDto getByStudentId(String studentId) {
		return checkIfStudentExist(studentId);
	}

	private StudentDto checkIfStudentExist(String studentId) {
		Optional<Student> optional = studentRepository.findByStudentId(studentId);

		if (optional.isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_STUDENT);
		}

		return modelMapper.map(optional.get(), StudentDto.class);
	}

	private CourseDto checkIfCourseExist(String courseId) {
		Optional<Course> optional = courseRepository.findByCourseId(courseId);

		if (optional.isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_COURSE);
		}

		return modelMapper.map(optional.get(), CourseDto.class);
	}

	@Override
	public Page<StudentDto> getAllStudents(int pageNum, int pageSize, String sortOptions, String search) {

		Sort sort = prepareSortByFields(sortOptions, propertyStudent);

		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

		Page<Student> studentPage = studentRepository.getAllStudentsByPageAndSearch(pageable, search);

		java.lang.reflect.Type typeToken = new TypeToken<List<StudentDto>>() {
		}.getType();

		List<StudentDto> listDto = modelMapper.map(studentPage.getContent(), typeToken);

		return new PageImpl<>(listDto, pageable, studentPage.getTotalElements());
	}

	private Sort prepareSortByFields(String sortOptions, Map<String, String> propertyMap) {
		Sort sort = Sort.unsorted();

		String[] sortFields = sortOptions.split(",");

		for (String sortField : sortFields) {
			String actualSortField = sortField.replace("-", "");

			if (sortField.startsWith("-")) {
				sort = sort.and(Sort.by(propertyMap.get(actualSortField)).descending());
			} else {
				sort = sort.and(Sort.by(propertyMap.get(actualSortField)).ascending());
			}
		}

		return sort;
	}

	@Override
	public void enrollForACourse(String studentId, String courseId) {
		StudentDto studentDto = checkIfStudentExist(studentId);
		
		CourseDto courseDto = checkIfCourseExist(courseId);
		
		EnrolledCourseId enrolledCourseId = new EnrolledCourseId(studentDto.getId(), courseDto.getId());
		
		Optional<EnrolledCourse> optionalEnrolled = enrolledCourseRepository.findById(enrolledCourseId);
		
		if (optionalEnrolled.isPresent()) {
			throw new AlreadyEnrolledForTheCourseException(courseId);
		}

		Student student = modelMapper.map(studentDto, Student.class);

		Course course = modelMapper.map(courseDto, Course.class);
		
		EnrolledCourse enrolledCourse = new EnrolledCourse();
		enrolledCourse.setCourse(course);
		enrolledCourse.setStudent(student);
		enrolledCourse.setEnrolledOn(new Date());
		
		course.getStudentEnrolled().add(student);

		courseRepository.save(course);
		enrolledCourseRepository.save(enrolledCourse);
	}

	@Override
	public Page<EnrolledCourseDto> getEnrolledCoursesForAStudent(String studentId, int pageNum, int pageSize,
			String sortOptions, String search) {
		StudentDto dto = checkIfStudentExist(studentId);

		if (dto.getCoursesTaken().isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_COURSE);
		}
		
		Sort sort = prepareSortByFields(sortOptions, propertyCourse);
		
		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
		
		int id = dto.getId();
		
		Page<EnrolledCourse> coursePage = courseRepository.findCoursesEnrolledByStudent(id, pageable, search);

		java.lang.reflect.Type typeToken = new TypeToken<List<EnrolledCourseDto>>() {}.getType();
		List<EnrolledCourseDto> listDto = modelMapper.map(coursePage.getContent(), typeToken);
		
		Page<EnrolledCourseDto> dtoPage = new PageImpl<>(listDto, pageable, coursePage.getTotalElements());
		
		return dtoPage;
	}

	@Override
	public StudentDto getStudentByEmail(String email) {
		Optional<Student> optional = studentRepository.findByEmail(email);
		
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		
		Student student = optional.get();
		
		return modelMapper.map(student, StudentDto.class);
	}
}
