package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.model.Course;

public interface FilterCourseRepository {
	
	Page<Course> getAllCourses(Pageable pageable, String filterField);

}
