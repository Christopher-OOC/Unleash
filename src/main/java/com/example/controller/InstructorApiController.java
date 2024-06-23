package com.example.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.BadRequestException;
import com.example.exceptions.PasswordNotTheSameException;
import com.example.model.dto.CourseDto;
import com.example.model.dto.ExaminationSessionDto;
import com.example.model.dto.InstructorDto;
import com.example.model.dto.QuestionDto;
import com.example.model.requestmodel.CourseRequestModel;
import com.example.model.requestmodel.ExaminationSessionRequestModel;
import com.example.model.requestmodel.InstructorRequestModel;
import com.example.model.requestmodel.QuestionRequestModel;
import com.example.model.responsemodel.CourseResponseModel;
import com.example.model.responsemodel.QuestionResponseModel;
import com.example.model.responsemodel.RequestStatusType;
import com.example.model.responsemodel.ResponseMessageModel;
import com.example.model.responsemodel.ResponseStatusType;
import com.example.service.CourseService;
import com.example.service.ExaminationSessionService;
import com.example.service.InstructorService;
import com.example.service.QuestionService;
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

	private Map<String, Object> propertyMap = Map.of("course_code", "courseCode", "course_name", "courseName",
			"date_created", "dateCreated");

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

		InstructorDto instructorDto = modelMapper.map(requestModel, InstructorDto.class);

		// Generate instructorId
		instructorDto.setInstructorId(PublicIdGeneratorUtils.generateId(30));

		instructorService.save(instructorDto);

		URI uri = URI.create("/v1/instructors/" + instructorDto.getInstructorId());

		ResponseMessageModel message = new ResponseMessageModel();
		message.setResponseStatusType(ResponseStatusType.SUCCESS);
		message.setRequestStatusType(RequestStatusType.CREATED);

		return ResponseEntity.created(uri).body(message);
	}

	@PostMapping("/{instructorId}/courses")
	public ResponseEntity<?> addACourse(@RequestBody @Valid CourseRequestModel courseModel,
			@PathVariable("instructorId") String instructorId) {

		CourseDto courseDto = modelMapper.map(courseModel, CourseDto.class);
		courseDto.setCourseId(PublicIdGeneratorUtils.generateId(30));

		courseService.addNewCourse(instructorId, courseDto);

		URI uri = URI.create("/v1/courses/" + courseDto.getCourseId());

		ResponseMessageModel message = new ResponseMessageModel();
		message.setResponseStatusType(ResponseStatusType.SUCCESS);
		message.setRequestStatusType(RequestStatusType.CREATED);

		return ResponseEntity.created(uri).body(message);
	}

	@GetMapping("/{instructorId}/courses")
	public ResponseEntity<?> getAllCoursesByInstructor(@PathVariable("instructorId") String instructorId,
			@RequestParam(value = "page", required = false, defaultValue = "1") @Positive(message = "Page number must be positive") int page,
			@RequestParam(value = "size", required = false, defaultValue = "3") @Positive(message = "Page size must be positive") int size,
			@RequestParam(value = "sort", required = false, defaultValue = "date_created") String sortFields) {

		// Validate Sort Fields
		String[] arrSortFields = sortFields.split(",");

		for (String field : arrSortFields) {
			String actualField = field.replace("-", "");
			if (!propertyMap.keySet().contains(actualField)) {
				throw new BadRequestException("Invalid sort field: " + actualField);
			}
		}

		Page<CourseDto> pageDto = instructorService.getAllInstructorCourses(instructorId, page, size, sortFields);

		List<CourseDto> listDto = pageDto.getContent();

		List<CourseResponseModel> listResponse = new ArrayList<>();

		for (CourseDto dto : listDto) {
			CourseResponseModel response = modelMapper.map(dto, CourseResponseModel.class);
			response.setNumberOfStudentEnrolled(dto.getStudentEnrolled().size());
			listResponse.add(response);
		}

		addSelfLinksToEachResponse(listResponse);

		PageMetadata pageMetadata = new PageMetadata(size, page, pageDto.getTotalElements(), pageDto.getTotalPages());

		CollectionModel<CourseResponseModel> collectionModel = PagedModel.of(listResponse, pageMetadata);

		return ResponseEntity.ok(collectionModel);
	}

	private void addSelfLinksToEachResponse(List<CourseResponseModel> listResponse) {
		listResponse.forEach(response -> {
			response.add(
					linkTo(methodOn(CourseApiController.class).getCourseById(response.getCourseId())).withSelfRel());
		});

	}

	@PostMapping("/{instructorId}/{courseId}/start-session")
	public ResponseEntity<?> createAnExaminaionSession(@RequestBody @Valid ExaminationSessionRequestModel requestModel,
			@PathVariable("instructotId") String instructorId,
			@PathVariable("courseId") String courseId) {

		ExaminationSessionDto dto = modelMapper.map(requestModel, ExaminationSessionDto.class);

		examinationSessionService.createExamSessionForACourse(instructorId, courseId, dto);

		ResponseMessageModel message = new ResponseMessageModel();
		message.setResponseStatusType(ResponseStatusType.SUCCESS);
		message.setRequestStatusType(RequestStatusType.CREATED);

		return ResponseEntity.created(null).body(message);
	}
	
	@PostMapping("/{instructorId}/{courseId}/end-session")
	public ResponseEntity<?> endAnExaminaionSession(
			@PathVariable("instructorId") String instructorId,
			@PathVariable("courseId") String courseId) {

		examinationSessionService.closeExamSessionForACourse(instructorId, courseId);

		ResponseMessageModel message = new ResponseMessageModel();
		message.setResponseStatusType(ResponseStatusType.SUCCESS);
		message.setRequestStatusType(RequestStatusType.CLOSED);

		return ResponseEntity.ok(message);
	}

	@PostMapping("/{instructorId}/{courseId}/questions")
	public ResponseEntity<?> addQuestionToCourse(@RequestBody @Valid QuestionRequestModel requestModel,
			@PathVariable("instructorId") String instructorId, @PathVariable("courseId") String courseId) {

		QuestionDto questionDto = modelMapper.map(requestModel, QuestionDto.class);

		QuestionDto savedQuestion = instructorService.addANewQuestionForACourse(questionDto, courseId, instructorId);

		QuestionResponseModel response = modelMapper.map(savedQuestion, QuestionResponseModel.class);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{instructorId}/{courseId}/questions")
	public ResponseEntity<?> updateAQuestionForACourse(@RequestBody @Valid QuestionRequestModel requestModel,
			@PathVariable("courseId") String courseId,
			@PathVariable("instructorId") String instructorId,
			@PathVariable("questionId") String questionId) {

		QuestionDto questionDto = modelMapper.map(requestModel, QuestionDto.class);

		QuestionDto updatedQuestion = instructorService.updateAQuestionForACourse(questionDto, courseId, instructorId, questionId);

		QuestionResponseModel response = modelMapper.map(updatedQuestion, QuestionResponseModel.class);

		return ResponseEntity.ok(response);
	}

}
