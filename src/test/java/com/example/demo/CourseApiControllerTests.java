package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.exceptions.NoCourseAvailableException;
import com.example.exceptions.NoSuchCourseFoundException;
import com.example.model.Course;
import com.example.model.dto.CourseDto;
import com.example.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseApiControllerTests {
	
	public static final String END_POINT_PATH = "/v1/courses";
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CourseService courseService;
	
	@Test
	public void testAddShouldReturn400BadRequestBecauseInvalidData() throws Exception {
		CourseDto courseDto = new CourseDto();
		courseDto.setCourseCode("");
		courseDto.setCourseName("A");
		
		String requestBody = objectMapper.writeValueAsString(courseDto);
		
		mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(requestBody))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}
	

	@Test
	public void testAddShouldReturn201Created() throws Exception {
		int instructorId = 3;
		
		CourseDto courseDto = new CourseDto();
		courseDto.setCourseCode("MEG503");
		courseDto.setCourseName("Fluid Dynamics");
		courseDto.setInstructorId(3);
		
		String requestBody = objectMapper.writeValueAsString(courseDto);
		
		Mockito.when(courseService.addNewCourse(dtoToEntity(courseDto), instructorId)).thenReturn(dtoToEntity(courseDto));
		
		mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(requestBody))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.course_code", is("MEG503")))
			.andExpect(jsonPath("$.course_name", is("Fluid Dynamics")))
			.andExpect(jsonPath("$.instructor_id", is(3)))
			.andDo(print());
	}
	
	@Test
	public void testListShouldReturn404NotFound() throws Exception {
		
		Mockito.when(courseService.getAllCourses()).thenThrow(NoCourseAvailableException.class);
		
		mockMvc.perform(get(END_POINT_PATH))
			.andExpect(status().isNotFound())
			.andDo(print());
	}
	
	@Test
	public void testListShouldReturn200Ok() throws Exception {
		Course course1 = new Course();
		course1.setCourseId(1);
		course1.setCourseCode("MEG503");
		course1.setCourseName("Fluid Dynamics");
		
		Course course2 = new Course();
		course2.setCourseId(1);
		course2.setCourseCode("MEG503");
		course2.setCourseName("Fluid Dynamics");
		course2.setDateCreated(new Date());
		
		
		Mockito.when(courseService.getAllCourses()).thenReturn(List.of(course1, course2));
		
		mockMvc.perform(get(END_POINT_PATH))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].course_code", is("MEG503")))
		.andExpect(jsonPath("$[0].course_name", is("Fluid Dynamics")))
		.andDo(print());
	}
	
	@Test
	public void testGetCourseByIdShouldReturn400BadRequest() throws Exception {
		String courseId = "-1";
		
		mockMvc.perform(get(END_POINT_PATH + "/" + courseId))
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
	
	@Test
	public void testGetCourseByIdShouldReturn404NotFound() throws Exception {
		String courseId = "112121212";
		
		Mockito.when(courseService.getCourseById(Mockito.anyInt())).thenThrow(NoSuchCourseFoundException.class);
		
		mockMvc.perform(get(END_POINT_PATH + "/" + courseId))
		.andExpect(status().isNotFound())
		.andDo(print());
	}
	
	@Test
	public void testGetCourseByIdShouldReturn200Ok() throws Exception {
		String courseId = "2";
		
		Course course = new Course();
		course.setCourseId(1);
		course.setCourseCode("MEG503");
		course.setCourseName("Fluid Dynamics");
		
		Mockito.when(courseService.getCourseById(Mockito.anyInt())).thenReturn(course);
		
		mockMvc.perform(get(END_POINT_PATH + "/" + courseId))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.course_code", is("MEG503")))
		.andExpect(jsonPath("$.course_name", is("Fluid Dynamics")))
		.andExpect(jsonPath("$.course_id", is(1)))
		.andDo(print());
	}
	
	
	private Course dtoToEntity(CourseDto dto) {
		return modelMapper.map(dto, Course.class);
	}
}
