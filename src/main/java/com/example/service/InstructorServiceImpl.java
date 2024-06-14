package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.exceptions.NoCourseAvailableException;
import com.example.exceptions.NoSuchInstructorException;
import com.example.model.dto.CourseDto;
import com.example.model.dto.InstructorDto;
import com.example.model.entity.Course;
import com.example.model.entity.Instructor;
import com.example.repository.CourseRepository;
import com.example.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService {

	private CourseRepository courseRepository;

	private InstructorRepository instructorRepository;

	private ModelMapper modelMapper;
	
	private Map<String, Object> propertyMap = Map.of("course_code", "courseCode", "course_name", "courseName",
			"date_created", "dateCreated");


	public InstructorServiceImpl(CourseRepository courseRepository, InstructorRepository instructorRepository,
			ModelMapper modelMapper) {
		super();
		this.courseRepository = courseRepository;
		this.instructorRepository = instructorRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public void save(InstructorDto instructorDto) {
		Instructor instructor = modelMapper.map(instructorDto, Instructor.class);

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
			throw new NoCourseAvailableException("No course created");
		}
		
		java.lang.reflect.Type typeToken = new TypeToken<List<CourseDto>>() {}.getType();
		List<CourseDto> listDto = modelMapper.map(coursePage.getContent(), typeToken);

		return new PageImpl<>(listDto, pageable, coursePage.getTotalElements());
	}

	private Instructor checkIfInstructorExists(String instructorId) {
		Optional<Instructor> optional = instructorRepository.findByInstructorId(instructorId);

		if (optional.isEmpty()) {
			throw new NoSuchInstructorException(instructorId);
		}

		return optional.get();
	}

}
