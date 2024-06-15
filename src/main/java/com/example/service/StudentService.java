package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.model.dto.CourseDto;
import com.example.model.dto.StudentDto;
import com.example.model.entity.Course;

public interface StudentService {
	
	void register(StudentDto studentDto);
	
	StudentDto getByStudentId(String studentId);
	
	Page<StudentDto> getAllStudents(int pageNum, int pageSize, String sortOptions, String search);
	
	void enrollForACourse(String studentId, String courseId);
	
	Page<CourseDto> getEnrolledCoursesForAStudent(String studentId, int pageNum, int pageSize, String sortOptions, String search);

}
