package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.model.entity.Course;
import com.example.model.entity.EnrolledCourse;

public interface FilterCourseRepository {
	
	Page<Course> getAllCourses(Pageable pageable, String search);
	
	Page<EnrolledCourse> findCoursesEnrolledByStudent(int studentId, Pageable pageable, String search);

}
