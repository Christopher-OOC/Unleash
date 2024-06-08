package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exceptions.NoCourseAvailableException;
import com.example.model.Course;
import com.example.model.Instructor;
import com.example.repository.CourseRepository;
import com.example.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService {
	
	private CourseRepository courseRepository;
	
	private InstructorRepository instructorRepository;

	public InstructorServiceImpl(CourseRepository courseRepository, InstructorRepository instructorRepository) {
		super();
		this.courseRepository = courseRepository;
		this.instructorRepository = instructorRepository;
	}
	

	@Override
	public Instructor save(Instructor instructor) {
		return instructorRepository.save(instructor);
	}

	@Override
	public List<Course> getAllInstructorCourses(int instructorId) {
		
		List<Course> courses = courseRepository.getCoursesFromInstructor(instructorId);
		
		if (courses.isEmpty()) {
			throw new NoCourseAvailableException("No course created");
		}
		
		return courses;
	}

}
