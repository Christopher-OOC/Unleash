package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>, FilterCourseRepository {
	
	Optional<Course> findByCourseId(String courseId);

}
