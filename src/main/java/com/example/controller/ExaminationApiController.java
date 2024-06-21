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
import com.example.model.dto.ExaminationQuestionAnswerDto;
import com.example.model.dto.ExaminationResultDto;
import com.example.model.dto.StudentDto;
import com.example.model.entity.ExaminationId;
import com.example.model.entity.ExaminationSession;
import com.example.model.entity.Student;
import com.example.model.responsemodel.ExaminationQuestionAnswerResponseModel;
import com.example.model.responsemodel.ExaminationResponseModel;
import com.example.model.responsemodel.RequestStatusType;
import com.example.model.responsemodel.ResponseMessageModel;
import com.example.model.responsemodel.ResponseStatusType;
import com.example.service.ExaminationService;
import com.example.service.StudentService;

@RestController
@RequestMapping("/v1/examinations")
public class ExaminationApiController {

	private ExaminationService examinationService;

	private StudentService studentService;

	private ModelMapper modelMapper;

	public ExaminationApiController(ExaminationService examinationService, StudentService studentService,
			ModelMapper modelMapper) {
		super();
		this.examinationService = examinationService;
		this.studentService = studentService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("/{studentId}/{courseId}")
	public ResponseEntity<?> startExamination(@PathVariable("studentId") String studentId,
			@PathVariable("courseId") String courseId) {
		ExaminationDto examinationDto = examinationService.startExamination(courseId, studentId);

		int sessionId = examinationDto.getExaminationId().getSessionId().getExaminationSessionId();

		ExaminationResponseModel response = modelMapper.map(examinationDto, ExaminationResponseModel.class);
		response.setSessionId(sessionId);
		response.setStudentId(examinationDto.getExaminationId().getStudentId().getStudentId());

		ExaminationQuestionAnswerDto nextQuestion = examinationService.getNextQuestion(sessionId, studentId);

		ExaminationQuestionAnswerResponseModel responseNextQuestion = modelMapper.map(nextQuestion,
				ExaminationQuestionAnswerResponseModel.class);

		response.setNextQuestion(responseNextQuestion);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/{studentId}/{courseId}")
	public ResponseEntity<?> endExamination(@PathVariable("studentId") String studentId,
			@PathVariable("courseId") String courseId) {

		examinationService.endExamination(courseId, studentId);

		ResponseMessageModel message = new ResponseMessageModel();
		message.setRequestStatusType(RequestStatusType.ENDED);
		message.setResponseStatusType(ResponseStatusType.SUCCESS);

		return ResponseEntity.ok(message);
	}

	@GetMapping("/{studentId}/{courseId}/next_question")
	public ResponseEntity<?> submitPrevoiusAndGetNextQuestion(@PathVariable("studentId") String studentId,
			@RequestBody ExaminationResponseModel examInRequest) {

		ExaminationDto examDtoRequest = modelMapper.map(examInRequest, ExaminationDto.class);
		examDtoRequest.setPreviousQuestion(
				modelMapper.map(examInRequest.getNextQuestion(), ExaminationQuestionAnswerDto.class));

		ExaminationId id = new ExaminationId();

		StudentDto studentDto = studentService.getByStudentId(studentId);
		Student student = new Student();
		student.setId(studentDto.getId());

		ExaminationSession session = new ExaminationSession();
		session.setExaminationSessionId(examInRequest.getSessionId());

		id.setSessionId(session);
		id.setStudentId(student);

		examDtoRequest.setExaminationId(id);

		ExaminationDto submittedExaminationDto = examinationService.submitPrevoiusQuestion(examDtoRequest);

		ExaminationQuestionAnswerDto nextQuestionDto = examinationService.getNextQuestion(examInRequest.getSessionId(),
				examInRequest.getStudentId());

		var nextQuestionResponse = modelMapper.map(nextQuestionDto, ExaminationQuestionAnswerResponseModel.class);

		examInRequest.setNextQuestion(nextQuestionResponse);

		return ResponseEntity.ok(examInRequest);
	}

	@GetMapping("{studentId}/{courseId}/{sesionId}results")
	public ResponseEntity<?> checkResult(@PathVariable("studentId") String studentId,
			@PathVariable("courseId") String courseId,
			@PathVariable("sessionId") int sessionId) {
		
		ExaminationResultDto resultDto = examinationService.checkResult(sessionId, studentId);

		return ResponseEntity.ok(resultDto);
	}

}
