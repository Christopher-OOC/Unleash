package com.example.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exceptions.BadRequestException;
import com.example.model.dto.CourseDto;
import com.example.model.responsemodel.CourseResponseModel;
import com.example.service.CourseService;
import com.example.service.ExaminationSessionService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/v1/courses")
@Validated
public class CourseApiController {

	private CourseService courseService;

	private ModelMapper modelMapper;

	private Map<String, Object> propertyMap = Map.of("course_code", "courseCode", "course_name", "courseName");

	public CourseApiController(CourseService courseService, ExaminationSessionService examinationSessionService,
			ModelMapper modelMapper) {
		super();
		this.courseService = courseService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public ResponseEntity<?> listAllCourses(
			@RequestParam(value = "page", required = false, defaultValue = "1") @Min(value = 1, message = "Page number must not be less than 1") int pageNum,
			@RequestParam(value = "size", required = false, defaultValue = "3") @Min(value = 1, message = "Page size must not be less than 1") @Max(value = 10, message = "Page size must not be greather than 10") int pageSize,
			@RequestParam(value = "sort", required = false, defaultValue = "course_name") String sortOptions,
			@RequestParam(value = "search", required = false, defaultValue = "") String search) {

		// Validate sort options
		String[] arraySortOptions = sortOptions.split(",");

		for (String sortOption : arraySortOptions) {
			String actualSortOption = sortOption.replace("-", "");

			if (!propertyMap.containsKey(actualSortOption)) {
				throw new BadRequestException("Invalid sort field: " + actualSortOption);
			}
		}

		Page<CourseDto> pageCourses = courseService.getAllCourses(pageNum - 1, pageSize, sortOptions, search);

		int size = pageCourses.getSize();
		int number = pageCourses.getNumber();
		long totalElements = pageCourses.getTotalElements();
		int totalPages = pageCourses.getTotalPages();

		PageMetadata metadata = new PageMetadata(size, number + 1, totalElements, totalPages);

		List<CourseDto> listDto = pageCourses.getContent();

		List<CourseResponseModel> listResponse = listDtoToListResponse(listDto);

		addSelfLinksToCourses(listResponse);

		CollectionModel<CourseResponseModel> collectionModel = PagedModel.of(listResponse, metadata);

		addNavigationLiksToCollectionModel(collectionModel, pageNum, pageSize, sortOptions, search, totalPages);

		return ResponseEntity.ok(collectionModel);
	}

	private List<CourseResponseModel> listDtoToListResponse(List<CourseDto> listDto) {
		List<CourseResponseModel> listResponse = new ArrayList<>();

		for (CourseDto dto : listDto) {
			CourseResponseModel response = modelMapper.map(dto, CourseResponseModel.class);
			response.setNumberOfStudentEnrolled(dto.getStudentEnrolled().size());
			listResponse.add(response);
		}

		return listResponse;
	}

	private void addNavigationLiksToCollectionModel(CollectionModel<CourseResponseModel> collectionModel, int pageNum,
			int pageSize, String sortOptions, String filterField, int totalPages) {

		// Add self link
		collectionModel.add(
				linkTo(methodOn(getClass()).listAllCourses(pageNum, pageSize, sortOptions, filterField)).withSelfRel());

		if (pageNum < totalPages) {
			collectionModel
					.add(linkTo(methodOn(getClass()).listAllCourses(pageNum + 1, pageSize, sortOptions, filterField))
							.withRel(IanaLinkRelations.NEXT));

			collectionModel
					.add(linkTo(methodOn(getClass()).listAllCourses(totalPages, pageSize, sortOptions, filterField))
							.withRel(IanaLinkRelations.LAST));
		}

		if (pageNum > 1) {

			collectionModel
					.add(linkTo(methodOn(getClass()).listAllCourses(pageNum - 1, pageSize, sortOptions, filterField))
							.withRel(IanaLinkRelations.PREV));
		}

	}

	private void addSelfLinksToCourses(List<CourseResponseModel> listResponse) {
		listResponse.forEach(courseResponse -> {
			courseResponse.add(linkTo(methodOn(getClass()).getCourseById(courseResponse.getCourseId())).withSelfRel());
		});

	}

	@GetMapping("/{courseId}")
	public ResponseEntity<?> getCourseById(@PathVariable("courseId") String courseId) {
		CourseDto courseDto = courseService.getCourseById(courseId);

		CourseResponseModel courseResponse = modelMapper.map(courseDto, CourseResponseModel.class);
		courseResponse.setNumberOfStudentEnrolled(courseDto.getStudentEnrolled().size());
		
		addSelfLinksToCourses(List.of(courseResponse));

		return ResponseEntity.ok(courseResponse);
	}
}
