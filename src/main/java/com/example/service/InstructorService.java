package com.example.service;


import org.springframework.data.domain.Page;

import com.example.model.dto.CourseDto;
import com.example.model.dto.InstructorDto;
import com.example.model.dto.QuestionDto;

public interface InstructorService {
	
	Page<CourseDto> getAllInstructorCourses(String instructorId, int page, int size, String sortFields);

	void save(InstructorDto instructorDto);
	
	QuestionDto addANewQuestionForACourse(QuestionDto questionDto, String courseId);
	
	QuestionDto updateAQuestionForACourse(QuestionDto questionDto, String questionId);
	
	

}
