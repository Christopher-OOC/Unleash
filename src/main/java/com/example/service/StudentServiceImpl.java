package com.example.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.exceptions.NoCourseAvailableException;
import com.example.exceptions.NoStudentAvailableException;
import com.example.exceptions.NoSuchCourseFoundException;
import com.example.exceptions.NoSuchStudentFoundException;
import com.example.model.dto.CourseDto;
import com.example.model.dto.StudentDto;
import com.example.model.entity.Course;
import com.example.model.entity.Student;
import com.example.repository.CourseRepository;
import com.example.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;

	private CourseRepository courseRepository;
	
	private ModelMapper modelMapper;

	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
			ModelMapper modelMapper) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public void register(StudentDto dto) {
		Student student = modelMapper.map(dto, Student.class);
		
		studentRepository.save(student);
	}

	@Override
	public StudentDto getByStudentId(String studentId) {
		return checkIfStudentExist(studentId);
	}

	private StudentDto checkIfStudentExist(String studentId) {
		Optional<Student> optional = studentRepository.findByStudentId(studentId);

		if (optional.isEmpty()) {
			throw new NoSuchStudentFoundException(studentId);
		}

		return modelMapper.map(optional.get(), StudentDto.class);
	}

	private Course checkIfCourseExist(String courseId) {
		Optional<Course> optional = courseRepository.findByCourseId(courseId);

		if (optional.isEmpty()) {
			throw new NoSuchCourseFoundException("" + courseId);
		}

		return optional.get();
	}

	@Override
	public List<Student> getAllStudents() {
		return checkIfAnyCourse();
	}

	private List<Student> checkIfAnyCourse() {
		List<Student> allStudents = studentRepository.findAll();

		if (allStudents.isEmpty()) {
			throw new NoStudentAvailableException("No student available");
		}

		return allStudents;
	}

	@Override
	public Course enrollForACourse(String studentId, String courseId) {
		StudentDto dto = checkIfStudentExist(studentId);

		Course course = checkIfCourseExist(courseId);
		
		Student student = modelMapper.map(dto, Student.class);

		student.getCoursesTaken().add(course);

		studentRepository.save(student);

		return course;
	}

	@Override
	public List<CourseDto> getEnrolledCoursesForAStudent(String studentId) {
		StudentDto dto = checkIfStudentExist(studentId);
		
		if (dto.getCoursesTaken().isEmpty()) {
			throw new NoCourseAvailableException("You don't have any enrolled course");
		}
		
		return dto.getCoursesTaken();
	}
}
