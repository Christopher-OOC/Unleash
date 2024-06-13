package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.model.entity.Course;
import com.example.model.entity.Instructor;
import com.example.repository.CourseRepository;
import com.example.repository.InstructorRepository;
  
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class CourseRepositoryTests {
	
	@Autowired
	private CourseRepository courseRepository;
	

	@Autowired
	private InstructorRepository instructorRepository;
	
	@Test
	public void testGetAllCoursesByInstructor() {
		
		Course course1 = new Course();
		course1.setCourseCode("MEG503");
		course1.setCourseName("Fluid Dynamics");
		course1.setDateCreated(new Date());
		
		Course course2 = new Course();
		course2.setCourseCode("MEG503");
		course2.setCourseName("Fluid Dynamics");
		course2.setDateCreated(new Date());
		
		Course course3 = new Course();
		course3.setCourseCode("MEG503");
		course3.setCourseName("Fluid Dynamics");
		course3.setDateCreated(new Date());
		
		Instructor instructor = new Instructor();
		instructor.setFullName("Olojede Christopher");
		instructor.setDateRegistered(new Date());
		
		course1.setInstructor(instructor);
		course2.setInstructor(instructor);
		course3.setInstructor(instructor);
		
		instructor.getCoursesTaken().add(course1);
		instructor.getCoursesTaken().add(course2);
		instructor.getCoursesTaken().add(course3);
		
		
		
		Instructor savedInstructor = instructorRepository.save(instructor);
		
		List<Course> listCourses = courseRepository.getCoursesFromInstructor(savedInstructor.getInstructorId());
		
		log.info("Saved Instructor Courses: {}", savedInstructor.getCoursesTaken().size());
		log.info("List Courses: {}", listCourses.size());
		
		assertThat(savedInstructor).isNotNull();
		assertThat(listCourses).isNotNull();
		assertThat(savedInstructor.getCoursesTaken().size()).isEqualTo(listCourses.size());
		
	}
}
