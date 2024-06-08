package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer>, FilterCourseRepository {
	
	@Query("SELECT c FROM Course c WHERE c.instructor.instructorId = ?1")
	List<Course> getCoursesFromInstructor(int intructorId);

}
