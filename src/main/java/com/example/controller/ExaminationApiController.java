package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.dto.ExaminationDto;
import com.example.model.dto.ExaminationResultDto;
import com.example.model.entity.Examination;
import com.example.service.ExaminationService;

@RestController
@RequestMapping("/v1/examinations")
public class ExaminationApiController {

	private ExaminationService examinationService;

	private ModelMapper modelMapper;

	public ExaminationApiController(ExaminationService examinationService, ModelMapper modelMapper) {
		super();
		this.examinationService = examinationService;
		this.modelMapper = modelMapper;
	}

	@GetMapping
	public ResponseEntity<?> startExamination(@RequestParam("courseId") int courseId,
			@RequestParam("studentId") int studentId) {
		ExaminationDto examinationDto = examinationService.startExamination(courseId, studentId);

		return ResponseEntity.ok(examinationDto);
	}

	@PostMapping
	public ResponseEntity<?> endExamination(@RequestParam("courseId") int courseId,
			@RequestParam("studentId") int studentId) {
		Examination examination = examinationService.endExamination(courseId, studentId);

		return ResponseEntity.ok(entityToDto(examination));
	}

	@GetMapping("/next_question")
	public ResponseEntity<?> submitPrevoiusAndGetNextQuestion(@RequestBody ExaminationDto examDto) {
		ExaminationDto nextQuestion = examinationService.submitPrevoiusAndGetNextQuestion(examDto);
		
		return ResponseEntity.ok(nextQuestion);
	}

	@GetMapping("/results")
	public ResponseEntity<?> checkResult(@RequestParam("sessionId") int sessionId,
			@RequestParam("studentId") int studentId) {
		ExaminationResultDto resultDto = examinationService.checkResult(sessionId, studentId);

		return ResponseEntity.ok(resultDto);
	}

	private ExaminationDto entityToDto(Examination examination) {
		return modelMapper.map(examination, ExaminationDto.class);
	}

}
