package com.example.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Course;
import com.example.model.Student;
import com.example.model.dto.CourseDto;
import com.example.model.dto.StudentDto;
import com.example.repository.StudentRepository;
import com.example.service.StudentService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/students")
public class StudentApiController {
	
	private StudentService studentService;
	
	private ModelMapper modelMapper;

	public StudentApiController(StudentService studentService, ModelMapper modelMapper) {
		super();
		this.studentService = studentService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping
	public ResponseEntity<?> register(@RequestBody @Valid StudentDto studentDto) {
		Student student = studentService.register(modelMapper.map(studentDto, Student.class));
		
		StudentDto savedDto = modelMapper.map(student, StudentDto.class);
		
		URI uri = URI.create("/v1/students/" + savedDto.getStudentId());
		
		return ResponseEntity.created(uri).body(savedDto);
	}
	
	@GetMapping("/{studentId}")
	public ResponseEntity<?> getStudentById(@PathVariable("studentId") @Positive(message="studentId must be positive") int studentId) {
		Student student = studentService.getById(studentId);
		
		StudentDto dto = modelMapper.map(student, StudentDto.class);
		
		return ResponseEntity.ok(dto);
	}
	
	@GetMapping
	public ResponseEntity<?> getAllStudent() {
		List<Student> allStudents = studentService.getAllStudents();
		
		List<StudentDto> dtos = listEntityToListDto(allStudents);
		
		return ResponseEntity.ok(dtos);
	}
	
	@PostMapping("/{studentId}/courses/{courseId}")
	public ResponseEntity<?> enrollForACourse(@PathVariable("studentId") int studentId, @PathVariable("courseId") int courseId) {
		Course enrolledCourse = studentService.enrollForACourse(studentId, courseId);
		
		URI uri = URI.create("/v1/students/" + studentId + "/courses/" + courseId);
		
		return ResponseEntity.created(uri).body(modelMapper.map(enrolledCourse, CourseDto.class));
	}
	
	@GetMapping("/{studentId}/courses")
	public ResponseEntity<?> getAllCourseEnrolledByStudent(@PathVariable("studentId") int studentId) {
		List<Course> allEnrolledCourses = studentService.getEnrolledCoursesForAStudent(studentId);
		
		List<CourseDto> dtos = listEntityToDtos(allEnrolledCourses)	;
		
		return ResponseEntity.ok(dtos);
	}
	
	private List<CourseDto> listEntityToDtos(List<Course> allEnrolledCourses) {
		List<CourseDto> dtos = new ArrayList<>();
		
		allEnrolledCourses.forEach(course -> {
			dtos.add(modelMapper.map(course, CourseDto.class));
		});
		
		return dtos;
	}

	private List<StudentDto> listEntityToListDto(List<Student> listStudents) {
		List<StudentDto> dtos = new ArrayList<>();
		
		listStudents.forEach(student -> {
			dtos.add(modelMapper.map(student, StudentDto.class));
		});
		
		return dtos;
	}

}
