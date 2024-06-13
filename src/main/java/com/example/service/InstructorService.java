package com.example.service;

import java.util.List;

import com.example.model.entity.Course;
import com.example.model.entity.Instructor;

public interface InstructorService {
	
	List<Course> getAllInstructorCourses(int instructorId);

	Instructor save(Instructor instructor);
	
	

}
