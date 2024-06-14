package com.example.service;

import org.springframework.data.domain.Page;

import com.example.model.dto.CourseDto;
import com.example.model.entity.Course;

public interface CourseService {
	
	Course addNewCourse(String instructorId, CourseDto courseDto);
	
	CourseDto getCourseById(String courseId);
	
	Page<CourseDto> getAllCourses(int pageNum, int pageSize, String sortFields, String search);

}
