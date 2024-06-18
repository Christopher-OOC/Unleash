package com.example.controller;

import java.net.URI;


import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.dto.QuestionDto;
import com.example.model.entity.Question;
import com.example.model.requestmodel.QuestionRequestModel;
import com.example.model.responsemodel.QuestionResponseModel;
import com.example.model.responsemodel.RequestStatusType;
import com.example.model.responsemodel.ResponseMessageModel;
import com.example.model.responsemodel.ResponseStatusType;
import com.example.service.CourseService;
import com.example.service.QuestionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/questions")
@Validated
public class QuestionApiController {
	
	private QuestionService questionService;
		
	private ModelMapper modelMapper;

	public QuestionApiController(QuestionService questionService, CourseService courseService,
			ModelMapper modelMapper) {
		super();
		this.questionService = questionService;
		this.modelMapper = modelMapper;
	}

}
