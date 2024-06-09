package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.exceptions.NoCourseAvailableException;
import com.example.exceptions.NoSuchCourseFoundException;
import com.example.exceptions.NoSuchInstructorException;
import com.example.model.Course;
import com.example.model.Instructor;
import com.example.repository.CourseRepository;
import com.example.repository.InstructorRepository;

@Service
public class CourseServiceImpl implements CourseService {
	
	private CourseRepository courseRepository;
	
	private InstructorRepository instructorRepository;
	
	private Map<String, Object> propertyMap = Map.of("course_code", "courseCode", "course_name", "courseName");

	public CourseServiceImpl(CourseRepository courseRepository, InstructorRepository instructorRepository) {
		super();
		this.courseRepository = courseRepository;
		this.instructorRepository = instructorRepository;
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
	public Course getCourseById(int courseId) {
		return checkIfCourseExists(courseId);
	}

	private Course checkIfCourseExists(int courseId) {
		Optional<Course> optional = courseRepository.findById(courseId);
		
		if (optional.isEmpty()) {
			throw new NoSuchCourseFoundException(courseId);
		}
		
		return optional.get();
	}

	@Override
	@Deprecated
	public List<Course> getAllCourses() {
		List<Course> list = courseRepository.findAll();
		
		if (list == null) {
			throw new NoCourseAvailableException("No courses available");
		}
		
		return list;
	}
	
	@Override
	public Page<Course> getAllCourses(int pageNum, int pageSize, String sortFields, String search) {
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
		
		return courseRepository.getAllCourses(pageable, search);
	}

}
