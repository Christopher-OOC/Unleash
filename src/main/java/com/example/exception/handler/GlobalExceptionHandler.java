package com.example.exception.handler;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.exceptions.AlreadyEnrolledForTheCourseException;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.ExaminationCompletionException;
import com.example.exceptions.ExaminationNoLongerAvailableException;
import com.example.exceptions.ExaminationSessionAlreadyCreated;
import com.example.exceptions.NotRegisteredForTheCourseException;
import com.example.exceptions.OngoingExaminationException;
import com.example.exceptions.PasswordNotTheSameException;
import com.example.exceptions.ResultNotAvailableException;
import com.example.model.error.ErrorMessage;
import com.example.model.error.ErrorMessages;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(com.example.exceptions.NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleNoSuchInstructorException(HttpServletRequest request, com.example.exceptions.NoResourceFoundException ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setPath(request.getServletPath());
		error.setMessage(ex.getMessage());

		return error;
	}

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleBadRequestException(HttpServletRequest request, BadRequestException ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(request.getServletPath());
		error.setMessage(ex.getMessage());

		return error;
	}

	@ExceptionHandler(NotRegisteredForTheCourseException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleNotRegisteredForTheCourseException(HttpServletRequest request,
			NotRegisteredForTheCourseException ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setPath(request.getServletPath());
		error.setMessage(ex.getMessage());

		return error;
	}

	@ExceptionHandler(PasswordNotTheSameException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handlePasswordNotTheSameException(HttpServletRequest request, PasswordNotTheSameException ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(request.getServletPath());
		error.setMessage(ex.getMessage());

		return error;
	}

	@ExceptionHandler(ExaminationNoLongerAvailableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleExaminationNoLongerAvailableException(HttpServletRequest request,
			ExaminationNoLongerAvailableException ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(request.getServletPath());
		error.setMessage(ex.getMessage());
		return error;
	}

	@ExceptionHandler(ExaminationCompletionException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ErrorMessage handleExaminationCompletionException(HttpServletRequest request,
			ExaminationCompletionException ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.NO_CONTENT.value());
		error.setPath(request.getServletPath());
		System.out.println(ex.getMessage());
		error.setMessage(ex.getMessage());
		
		return error;
	}
	
	@ExceptionHandler(ResultNotAvailableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleResultNotAvailableException(HttpServletRequest request,
			ResultNotAvailableException ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.NO_CONTENT.value());
		error.setPath(request.getServletPath());
		System.out.println(ex.getMessage());
		error.setMessage(ex.getMessage());
		
		return error;
	}
	
	@ExceptionHandler(OngoingExaminationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleOngoingExaminationException(HttpServletRequest request,
			OngoingExaminationException ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(request.getServletPath());
		System.out.println(ex.getMessage());
		error.setMessage(ex.getMessage());

		return error;
	}

	@ExceptionHandler(AlreadyEnrolledForTheCourseException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleAlreadyEnrolledForTheCourseException(HttpServletRequest request,
			AlreadyEnrolledForTheCourseException ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(request.getServletPath());
		error.setMessage(ex.getMessage());
		
		return error;
	}
	
	@ExceptionHandler(ExaminationSessionAlreadyCreated.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handleConstraintViolationException(HttpServletRequest request,
			ExaminationSessionAlreadyCreated ex) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(request.getServletPath());
		error.setMessage(ex.getMessage());
		
		return error;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessages handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {

		ErrorMessages error = new ErrorMessages();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(request.getServletPath());

		ex.getConstraintViolations().forEach(violation -> {
			error.getMessages().add(violation.getMessage());
		});

		return error;
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorMessages error = new ErrorMessages();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(((ServletWebRequest) request).getRequest().getServletPath());

		List<FieldError> fieldErrors = ex.getFieldErrors();

		fieldErrors.forEach(fieldError -> {
			error.getMessages().add(fieldError.getDefaultMessage());
		});

		return new ResponseEntity<>(error, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(((ServletWebRequest) request).getRequest().getServletPath());

		error.setMessage("The route is not valid");

		return new ResponseEntity<>(error, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		ErrorMessage error = new ErrorMessage();

		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setPath(((ServletWebRequest) request).getRequest().getServletPath());

		error.setMessage("There must be information provided in the request body");

		return new ResponseEntity<>(error, headers, status);
	}
	
//	@ExceptionHandler(Exception.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public ErrorMessage handleGeneralException(HttpServletRequest request, Exception ex) {
//
//		ErrorMessage error = new ErrorMessage();
//
//		error.setStatus(HttpStatus.BAD_REQUEST.value());
//		error.setPath(request.getServletPath());
//		error.setMessage(ex.getMessage());
//
//		return error;
//	}

}
