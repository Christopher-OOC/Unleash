package com.example.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.BadRequestException;
import com.example.model.Course;
import com.example.model.ExaminationSession;
import com.example.model.dto.CourseDto;
import com.example.model.dto.ExaminationSessionDto;
import com.example.service.CourseService;
import com.example.service.ExaminationSessionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/courses")
@Validated
public class CourseApiController {

	private CourseService courseService;

	private ModelMapper modelMapper;

	private ExaminationSessionService examinationSessionService;

	private Map<String, Object> propertyMap = Map.of("course_code", "courseCode", "course_name", "courseName");

	public CourseApiController(CourseService courseService, ExaminationSessionService examinationSessionService,
			ModelMapper modelMapper) {
		super();
		this.courseService = courseService;
		this.modelMapper = modelMapper;
		this.examinationSessionService = examinationSessionService;
	}

	/**
	 * Here, We can sort by course_code, course_name in any order but only filter by
	 * instructor_name
	 */
	@GetMapping
	public ResponseEntity<?> listAllCourses(
			@RequestParam(value = "page", required = false, defaultValue = "1") @Min(value = 1, message = "Page number must not be less than 1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "3") @Min(value = 1, message = "Page size must not be less than 1") @Max(value = 10, message = "Page size must not be greather than 10") int pageSize,
			@RequestParam(value = "sort", required = false, defaultValue = "course_name") String sortOptions,
			@RequestParam(value = "instructor_name", required = false, defaultValue = "") String filterField) {

		// Validate sort options
		String[] arraySortOptions = sortOptions.split(",");

		for (String sortOption : arraySortOptions) {
			String actualSortOption = sortOption.replace("-", "");

			if (!propertyMap.containsKey(actualSortOption)) {
				throw new BadRequestException("Invalid sort field: " + actualSortOption);
			}
		}

		Page<Course> pageCourses = courseService.getAllCourses(pageNum - 1, pageSize, sortOptions, filterField);

		int size = pageCourses.getSize();
		int number = pageCourses.getNumber();
		long totalElements = pageCourses.getTotalElements();
		int totalPages = pageCourses.getTotalPages();

		PageMetadata metadata = new PageMetadata(size, number + 1, totalElements, totalPages);

		List<Course> listContent = pageCourses.getContent();

		List<CourseDto> dtos = listEntityToListDto(listContent);

		addSelfLinksToCourses(dtos);

		CollectionModel<CourseDto> collectionModel = PagedModel.of(dtos, metadata);

		addNavigationLiksToCollectionModel(collectionModel, pageNum, pageSize, sortOptions, filterField, totalPages);

		return ResponseEntity.ok(collectionModel);
	}

	private void addNavigationLiksToCollectionModel(CollectionModel<CourseDto> collectionModel, int pageNum,
			int pageSize, String sortOptions, String filterField, int totalPages) {
		
		// Add self link
		collectionModel.add(linkTo(methodOn(getClass()).listAllCourses(pageNum, pageSize, sortOptions, filterField)).withSelfRel());
		
		if (pageNum < totalPages) {
			collectionModel.add(linkTo(methodOn(getClass()).listAllCourses(pageNum + 1, pageSize, sortOptions, filterField)).withRel(IanaLinkRelations.NEXT));
		
			 collectionModel.add(linkTo(methodOn(getClass()).listAllCourses(totalPages, pageSize, sortOptions, filterField)).withRel(IanaLinkRelations.LAST));
		}
		
		if (pageNum > 1) {
			
			collectionModel.add(linkTo(methodOn(getClass()).listAllCourses(pageNum - 1, pageSize, sortOptions, filterField)).withRel(IanaLinkRelations.PREV));
		}
		
	}

	private void addSelfLinksToCourses(List<CourseDto> dtos) {
		dtos.forEach(dto -> {
			dto.add(linkTo(methodOn(getClass()).getCourseById(dto.getCourseId())).withSelfRel());
		});

	}

	@GetMapping("/{courseId}")
	public ResponseEntity<?> getCourseById(
			@PathVariable("courseId") @Positive(message = "courseId must be positive") int courseId) {
		Course course = courseService.getCourseById(courseId);

		return ResponseEntity.ok(entityToDto(course));
	}

	@PostMapping("/exams/{courseId}")
	public ResponseEntity<?> createAnExaminaionSession(@RequestBody @Valid ExaminationSessionDto dto,
			@PathVariable("courseId") @Valid int courseId) {

		ExaminationSession savedExamSession = examinationSessionService.createExamSessionForACourse(dto, courseId);

		URI uri = URI.create("/v1/courses/" + courseId + "/" + savedExamSession.getExaminationSessionId());

		return ResponseEntity.created(uri).body(modelMapper.map(savedExamSession, ExaminationSessionDto.class));
	}

	private CourseDto entityToDto(Course course) {
		CourseDto dto = modelMapper.map(course, CourseDto.class);

		return dto;
	}

	@SuppressWarnings("unused")
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
