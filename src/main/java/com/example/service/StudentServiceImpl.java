package com.example.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.exceptions.NoCourseAvailableException;
import com.example.exceptions.NoStudentAvailableException;
import com.example.exceptions.NoSuchCourseFoundException;
import com.example.exceptions.NoSuchStudentFoundException;
import com.example.model.entity.Course;
import com.example.model.entity.Student;
import com.example.repository.CourseRepository;
import com.example.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	private StudentRepository studentRepository;

	private CourseRepository courseRepository;

	public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
			ModelMapper modelMapper) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public Student register(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student getById(int studentId) {
		return checkIfStudentExist(studentId);
	}

	private Student checkIfStudentExist(int studentId) {
		Optional<Student> optional = studentRepository.findById(studentId);

		if (optional.isEmpty()) {
			throw new NoSuchStudentFoundException(studentId);
		}

		return optional.get();
	}

	private Course checkIfCourseExist(int courseId) {
		Optional<Course> optional = courseRepository.findById(courseId);

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
	public Course enrollForACourse(int studentId, int courseId) {
		Student student = checkIfStudentExist(studentId);

		Course course = checkIfCourseExist(courseId);

		student.getCoursesTaken().add(course);

		studentRepository.save(student);

		return course;
	}

	@Override
	public List<Course> getEnrolledCoursesForAStudent(int studentId) {
		Student student = checkIfStudentExist(studentId);
		
		if (student.getCoursesTaken().isEmpty()) {
			throw new NoCourseAvailableException("You don't have any enrolled course");
		}
		
		return student.getCoursesTaken();
	}
}
