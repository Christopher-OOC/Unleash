package com.example.service;

import org.springframework.data.domain.Page;

import com.example.model.dto.CourseDto;
import com.example.model.dto.EnrolledCourseDto;
import com.example.model.dto.StudentDto;

public interface StudentService {
	
	void register(StudentDto studentDto);
	
	StudentDto getByStudentId(String studentId);
	
	Page<StudentDto> getAllStudents(int pageNum, int pageSize, String sortOptions, String search);
	
	void enrollForACourse(String studentId, String courseId);
	
	Page<EnrolledCourseDto> getEnrolledCoursesForAStudent(String studentId, int pageNum, int pageSize, String sortOptions, String search);

	StudentDto getStudentByEmail(String email);
	
}
