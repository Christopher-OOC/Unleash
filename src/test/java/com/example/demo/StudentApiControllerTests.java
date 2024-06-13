package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.exceptions.NoSuchStudentFoundException;
import com.example.model.dto.StudentDto;
import com.example.model.entity.Student;
import com.example.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class StudentApiControllerTests {
	
	public static final String END_POINT_PATH = "/v1/students";
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studentService;
	
	@Test
	public void testRegisterShouldReturn400BadRequest() throws Exception {
		StudentDto studentDto = new StudentDto();
		studentDto.setStudentId(1);
		studentDto.setDateOfBirth(new Date());
		studentDto.setPassword("");
		studentDto.setEmail("olamide");
		
		String requestBody = objectMapper.writeValueAsString(studentDto);
		
		mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(requestBody))
			.andExpect(status().isBadRequest())
			.andDo(print());
		
	}
	
	@Test
	public void testRegisterShouldReturn201Created() throws Exception {
		StudentDto studentDto = new StudentDto();
		studentDto.setStudentId(1);
		studentDto.setDateOfBirth(new Date());
		studentDto.setPassword("olamide24");
		studentDto.setEmail("olamide@gmail.com");
		studentDto.setFirstName("Olamide");
		studentDto.setLastName("Olojede");
		studentDto.setMiddleName("Christopher");
		
		String requestBody = objectMapper.writeValueAsString(studentDto);
		
		log.info("Request Body: " + requestBody);
		
		Mockito.when(studentService.register(dtoToEntity(studentDto))).thenReturn(dtoToEntity(studentDto));
		
		mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(requestBody))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.password", is("olamide24")))
			.andExpect(jsonPath("$.first_name", is("Olamide")))
			.andExpect(jsonPath("$.last_name", is("Olojede")))
			.andExpect(jsonPath("$.middle_name", is("Christopher")))
			.andDo(print());
		
	}
	
	@Test
	public void testGetStudentByIdShouldReturn400BadRequest() throws Exception {
		int studentId = -1;
		
		mockMvc.perform(get(END_POINT_PATH + "/" + studentId))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}
	
	@Test
	public void testGetStudentByIdShouldReturn404NotFound() throws Exception {
		int studentId = 1212;
		
		Mockito.when(studentService.getById(Mockito.anyInt())).thenThrow(NoSuchStudentFoundException.class);
		
		mockMvc.perform(get(END_POINT_PATH + "/" + studentId))
			.andExpect(status().isNotFound())
			.andDo(print());
	}
	
	@Test
	public void testGetStudentByIdShouldReturn200Ok() throws Exception {
		int studentId = 1212;
		
		Student student = new Student();
		student.setStudentId(studentId);
		student.setDateOfBirth(new Date());
		student.setPassword("olamide24");
		student.setEmail("olamide@gmail.com");
		student.setFirstName("Olamide");
		student.setLastName("Olojede");
		student.setMiddleName("Christopher");
		
		Mockito.when(studentService.getById(Mockito.anyInt())).thenReturn(student);
		
		mockMvc.perform(get(END_POINT_PATH + "/" + studentId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.password", is("olamide24")))
			.andExpect(jsonPath("$.first_name", is("Olamide")))
			.andExpect(jsonPath("$.last_name", is("Olojede")))
			.andExpect(jsonPath("$.middle_name", is("Christopher")))
			.andDo(print());
	}
	
	
	private Student dtoToEntity(StudentDto dto) {
		return modelMapper.map(dto, Student.class);
	}
	

}
