package com.example.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.PasswordNotTheSameException;
import com.example.model.dto.CourseDto;
import com.example.model.dto.ExaminationSessionDto;
import com.example.model.dto.InstructorDto;
import com.example.model.entity.Course;
import com.example.model.entity.ExaminationSession;
import com.example.model.entity.Instructor;
import com.example.model.requestmodel.InstructorRequestModel;
import com.example.service.CourseService;
import com.example.service.ExaminationSessionService;
import com.example.service.InstructorService;
import com.example.utilities.PublicIdGeneratorUtils;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/instructors")
@Validated
public class InstructorApiController {

	private InstructorService instructorService;

	private CourseService courseService;

	private ExaminationSessionService examinationSessionService;

	private ModelMapper modelMapper;

	public InstructorApiController(InstructorService instructorService, ModelMapper modelMapper,
			CourseService courseService, ExaminationSessionService examinationSessionService) {
		super();
		this.instructorService = instructorService;
		this.modelMapper = modelMapper;
		this.courseService = courseService;
		this.examinationSessionService = examinationSessionService;
	}

	@PostMapping
	public ResponseEntity<?> registerAsAnInstructor(@RequestBody @Valid InstructorRequestModel requestModel) {
		// Check if password and confirmPassword are the same
		
		if (!requestModel.getPassword().equals(requestModel.getConfirmPassword())) {
			throw new PasswordNotTheSameException();
		}
		
		InstructorDto dto = modelMapper.map(requestModel, InstructorDto.class);
		
		//Generate instructorId
		dto.setInstructorId(PublicIdGeneratorUtils.generateId(30));

//		Instructor savedInstructor = instructorService.save(instructor);
//
//		InstructorDto dto = instructorEntityToDto(savedInstructor);
//
//		URI uri = URI.create("/v1/instructors/" + dto.getInstructorId());

		return null;//ResponseEntity.created(uri).body(dto);
	}

	@PostMapping("/{instructorId}/courses")
	public ResponseEntity<?> addACourse(@RequestBody @Valid CourseDto courseDto,
			@PathVariable("instructorId") @Positive(message = "The instructor ID must be positive") int instructorId) {

		Course savedCourse = courseService.addNewCourse(dtoToEntity(courseDto), instructorId);

		CourseDto dto = entityToDto(savedCourse);

		URI uri = URI.create("/v1/courses/" + dto.getCourseId());

		return ResponseEntity.created(uri).body(dto);
	}

	@GetMapping("/{instructorId}/courses")
	public ResponseEntity<?> getAllCoursesByInstructor(
			@PathVariable("instructorId") @Positive(message = "Instuctor ID must be positive") int instructorId) {
		List<Course> courseList = instructorService.getAllInstructorCourses(instructorId);

		List<CourseDto> dtos = listEntityToListDto(courseList);

		return ResponseEntity.ok(dtos);
	}

	@PostMapping("/exam-session/{courseId}")
	public ResponseEntity<?> createAnExaminaionSession(@RequestBody @Valid ExaminationSessionDto dto,
			@PathVariable("courseId") @Valid int courseId) {

		ExaminationSession savedExamSession = examinationSessionService.createExamSessionForACourse(dto, courseId);

		URI uri = URI.create("/v1/courses/" + courseId + "/" + savedExamSession.getExaminationSessionId());

		return ResponseEntity.created(uri).body(modelMapper.map(savedExamSession, ExaminationSessionDto.class));
	}

	private Instructor instructorDtoToEntity(InstructorDto dto) {
		return modelMapper.map(dto, Instructor.class);
	}

	private InstructorDto instructorEntityToDto(Instructor instructor) {
		return modelMapper.map(instructor, InstructorDto.class);
	}

	private CourseDto entityToDto(Course course) {
		CourseDto dto = modelMapper.map(course, CourseDto.class);

		return dto;
	}

	private Course dtoToEntity(CourseDto dto) {
		Course course = modelMapper.map(dto, Course.class);

		return course;
	}

	private List<CourseDto> listEntityToListDto(List<Course> allCourses) {
		List<CourseDto> dtoList = new ArrayList<>();

		allCourses.forEach(course -> {
			dtoList.add(modelMapper.map(course, CourseDto.class));
		});

		return dtoList;
	}

}
