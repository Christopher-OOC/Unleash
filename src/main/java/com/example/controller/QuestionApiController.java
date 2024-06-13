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
import com.example.service.CourseService;
import com.example.service.QuestionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	@PostMapping("/{courseId}")
	public ResponseEntity<?> addQuestionToCourse(@RequestBody @Valid QuestionDto questionDto, @PathVariable("courseId") int courseId) {
		
		Question question = questionService.addANewQuestionForACourse(questionDto, courseId);
		
		log.info("Question: {}", question);
		
		URI uri = URI.create("/v1/questions/" + courseId + "/" + question.getQuestionId());
		
		QuestionDto dto = modelMapper.map(question, QuestionDto.class); 
		
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PatchMapping("/{questionId}")
	public ResponseEntity<?> updateAQuestionForACourse(@RequestBody @Valid QuestionDto questionDto, @PathVariable("questionId") @Valid int questionId) {
		
		Question updatedQuestion = questionService.updateAQuestionForACourse(questionDto, questionId);
		
		QuestionDto dto = modelMapper.map(updatedQuestion, QuestionDto.class); 
		
		return ResponseEntity.ok(dto);
	}

}
