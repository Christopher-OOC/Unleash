package com.example.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.model.dto.CourseDto;
import com.example.model.dto.ExaminationDto;
import com.example.model.entity.Course;
import com.example.model.entity.Examination;
import com.example.model.responsemodel.CourseResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class ConfigurationFile {
	
	@Bean
	ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
//		TypeMap<Examination, ExaminationDto> typeMap3 = modelMapper.typeMap(Examination.class, ExaminationDto.class);
//		typeMap3.addMapping(src -> src.getExaminationId().getSessionId().getExaminationSessionId(), ExaminationDto::setSessionId);
//		typeMap3.addMapping(src -> src.getExaminationId().getStudentId().getStudentId(), ExaminationDto::setStudentId);
		
//		TypeMap<ExaminationDto, Examination> typeMap4 = modelMapper.typeMap(ExaminationDto.class, Examination.class);
//		typeMap4.addMapping(src -> src.getSessionId(), (dest, value) -> dest.getExaminationId().getSessionId().setExaminationSessionId(value != null ? (int) value : -1));
//		typeMap4.addMapping(src -> src.getStudentId(), (dest, value) -> dest.getExaminationId().getStudentId().setStudentId(value != null ? (int) value : -1));
//		
		return modelMapper;
	}
	
	@Bean
	ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		
		return objectMapper;
	}

}
