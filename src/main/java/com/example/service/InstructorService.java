package com.example.service;


import org.springframework.data.domain.Page;

import com.example.model.dto.CourseDto;
import com.example.model.dto.InstructorDto;

public interface InstructorService {
	
	Page<CourseDto> getAllInstructorCourses(String instructorId, int page, int size, String sortFields);

	void save(InstructorDto instructorDto);
	
	

}
