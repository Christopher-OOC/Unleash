/*
package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.model.entity.Course;
import com.example.model.entity.Instructor;
import com.example.repository.InstructorRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class InstructorRepositoryTests {
	
	@Autowired
	private InstructorRepository instructorRepository;
	
	public static final int INSTRUCTOR_ID = 5;
	
	
	@Test
	public void testAddInstructor() {
		Instructor instructor = new Instructor();
		instructor.setInstructorId(INSTRUCTOR_ID);
		instructor.setFullName("David Hilbert");
		
		Instructor savedInstructor = instructorRepository.save(instructor);
		
		assertThat(savedInstructor).isNotNull();
	}
	
	@Test
	public void testAddInstructorWithCourses() {
		Instructor instructor = new Instructor();
		instructor.setInstructorId(INSTRUCTOR_ID);
		instructor.setFullName("David Hilbert");
		
		Course course1 = new Course();
		course1.setCourseCode("FEG501");
		course1.setCourseName("Engineering Economics");
		course1.setInstructor(instructor);
		
		Course course2 = new Course();
		course2.setCourseCode("MEG505");
		course2.setCourseName("Production Engineering");
		course2.setInstructor(instructor);
		
		instructor.getCoursesTaken().add(course1);
		instructor.getCoursesTaken().add(course2);
		
		Instructor savedInstructor = instructorRepository.save(instructor);
		
		assertThat(savedInstructor).isNotNull();
		assertThat(savedInstructor.getCoursesTaken().get(0).getCourseCode()).isEqualTo(course1.getCourseCode());		
	}

}
*/