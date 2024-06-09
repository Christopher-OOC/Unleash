package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.model.Course;

public interface CourseService {
	
	Course addNewCourse(Course course, int instructorId);
	
	Course getCourseById(int courseId);
	
	@Deprecated
	List<Course> getAllCourses();
	
	Page<Course> getAllCourses(int pageNum, int pageSize, String sortFields, String search);

}
