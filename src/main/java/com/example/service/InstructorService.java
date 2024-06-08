package com.example.service;

import java.util.List;

import com.example.model.Course;
import com.example.model.Instructor;

public interface InstructorService {
	
	List<Course> getAllInstructorCourses(int instructorId);

	Instructor save(Instructor instructor);
	
	

}
