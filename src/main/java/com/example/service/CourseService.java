package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.model.dto.CourseDto;
import com.example.model.entity.Course;

public interface CourseService {
	
	Course addNewCourse(Course course, int instructorId);
	
	CourseDto getCourseById(String courseId);
	
	Page<CourseDto> getAllCourses(int pageNum, int pageSize, String sortFields, String search);

}
