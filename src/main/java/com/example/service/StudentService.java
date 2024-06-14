package com.example.service;

import java.util.List;

import com.example.model.dto.CourseDto;
import com.example.model.dto.StudentDto;
import com.example.model.entity.Course;
import com.example.model.entity.Student;

public interface StudentService {
	
	void register(StudentDto studentDto);
	
	StudentDto getByStudentId(String studentId);
	
	List<Student> getAllStudents();
	
	Course enrollForACourse(String studentId, String courseId);
	
	List<CourseDto> getEnrolledCoursesForAStudent(String studentId);

}
