package com.example.controller;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.dto.CourseDto;
import com.example.model.dto.StudentDto;
import com.example.model.entity.Course;
import com.example.model.entity.Student;
import com.example.model.requestmodel.StudentRequestModel;
import com.example.model.responsemodel.RequestStatusType;
import com.example.model.responsemodel.ResponseMessageModel;
import com.example.model.responsemodel.ResponseStatusType;
import com.example.model.responsemodel.StudentResponseModel;
import com.example.service.StudentService;
import com.example.utilities.PublicIdGeneratorUtils;

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
	public ResponseEntity<?> register(@RequestBody @Valid StudentRequestModel requestModel) {
		StudentDto dto = modelMapper.map(requestModel, StudentDto.class);
		
		dto.setStudentId(PublicIdGeneratorUtils.generateId(30));
		
		studentService.register(dto);
		
		ResponseMessageModel message = new ResponseMessageModel();
		message.setResponseStatusType(ResponseStatusType.SUCCESS);
		message.setRequestStatusType(RequestStatusType.CREATED);

		return ResponseEntity.created(null).body(message);
	}

	@GetMapping("/{studentId}")
	public ResponseEntity<?> getStudentById(
			@PathVariable("studentId") String studentId) {
		StudentDto dto = studentService.getByStudentId(studentId);

		StudentResponseModel response = modelMapper.map(dto, StudentResponseModel.class);
		
		addSelfLinks(List.of(response));
		
		return ResponseEntity.ok(response);
	}

	private void addSelfLinks(List<StudentResponseModel> listResponse) {
		listResponse.forEach(response -> {
			response.add(linkTo(methodOn(getClass()).getStudentById(response.getStudentId())).withSelfRel());
		});
	}
	
	@GetMapping
	public ResponseEntity<?> getAllStudent() {
		List<Student> allStudents = studentService.getAllStudents();

		List<StudentDto> dtos = listEntityToListDto(allStudents);

		return ResponseEntity.ok(dtos);
	}
	

	@PostMapping("/{studentId}/courses/{courseId}")
	public ResponseEntity<?> enrollForACourse(@PathVariable("studentId") String studentId,
			@PathVariable("courseId") String courseId) {
		Course enrolledCourse = studentService.enrollForACourse(studentId, courseId);

		URI uri = URI.create("/v1/students/" + studentId + "/courses/" + courseId);

		return ResponseEntity.created(uri).body(modelMapper.map(enrolledCourse, CourseDto.class));
	}

	@GetMapping("/{studentId}/courses")
	public ResponseEntity<?> getAllCourseEnrolledByStudent(@PathVariable("studentId") String studentId) {
		List<CourseDto> allEnrolledCourses = studentService.getEnrolledCoursesForAStudent(studentId);


		return ResponseEntity.ok(allEnrolledCourses);
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
