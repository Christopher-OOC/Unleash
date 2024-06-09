package com.example.utilities;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.model.Student;
import com.example.model.dto.StudentDto;

@Component
public class ModelMapperUtils {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ModelMapperUtils() {
		
	}
	
	public StudentDto studentEntityToDto(Student student) {
		return modelMapper.map(student, StudentDto.class);
	}
	
	

}
