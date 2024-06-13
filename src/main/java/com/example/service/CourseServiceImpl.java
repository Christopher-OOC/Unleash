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
import com.example.exceptions.NoSuchCourseFoundException;
import com.example.exceptions.NoSuchInstructorException;
import com.example.model.dto.CourseDto;
import com.example.model.entity.Course;
import com.example.model.entity.Instructor;
import com.example.repository.CourseRepository;
import com.example.repository.InstructorRepository;

@Service
public class CourseServiceImpl implements CourseService {
	
	private CourseRepository courseRepository;
	
	private InstructorRepository instructorRepository;
	
	private ModelMapper modelMapper;
	
	private Map<String, Object> propertyMap = Map.of("course_code", "courseCode", "course_name", "courseName");

	public CourseServiceImpl(CourseRepository courseRepository, InstructorRepository instructorRepository, 
			ModelMapper modelMapper) {
		super();
		this.courseRepository = courseRepository;
		this.instructorRepository = instructorRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public Course addNewCourse(Course course, int instructorId) {
		
		Instructor instructor = checkIfInstructorExists(instructorId);
		
		course.setInstructor(instructor);
		
		return courseRepository.save(course);
	}

	private Instructor checkIfInstructorExists(int instructorId) {
		Optional<Instructor> optional = instructorRepository.findById(instructorId);
		
		if (optional.isEmpty()) {
			throw new NoSuchInstructorException(instructorId);
		}
		return optional.get();
	}

	@Override
	public CourseDto getCourseById(String courseId) {
		return checkIfCourseExists(courseId);
	}

	private CourseDto checkIfCourseExists(String courseId) {
		Optional<Course> optional = courseRepository.findByCourseId(courseId);
		
		if (optional.isEmpty()) {
			throw new NoSuchCourseFoundException(courseId);
		}
		
		Course course = optional.get();
		
		return modelMapper.map(course, CourseDto.class);
	}

	@Override
	public Page<CourseDto> getAllCourses(int pageNum, int pageSize, String sortFields, String filterField) {
		// initialize sort
		Sort sort = Sort.unsorted();
		
		String[] sortOptions = sortFields.split(",");
		
		for (String option : sortOptions) {
			String actualSortOption = option.replace("-", "");
			String value = (String) propertyMap.get(actualSortOption);
			if (option.startsWith("-")) {
				sort = sort.and(Sort.by(value).descending());
			}
			else {
				sort = sort.and(Sort.by(value).ascending());
			}
		}
		
		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
		
		Page<Course> returnPage = courseRepository.getAllCourses(pageable, filterField);
		
		List<Course> list = returnPage.getContent();
		
		java.lang.reflect.Type typeToken = new TypeToken<List<CourseDto>> () {}.getType();
		
		List<CourseDto> listDto = modelMapper.map(list, typeToken);
		
		return new PageImpl<CourseDto>(listDto, pageable, returnPage.getTotalElements());
	}

}
