package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.model.Course;
import com.example.model.Instructor;
import com.example.model.Student;
import com.example.repository.StudentRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class StudentRepositoryTests {
	
	@Autowired
	private StudentRepository studentRepository;
	
	private static final int STUDENT_ID = 1;
	
	@Test
	public void testAddStudent() {
		Student student = new Student();
		student.setStudentId(STUDENT_ID);
		student.setFirstName("Olamide");
		student.setLastName("Olojede");
		student.setMiddleName("Christopher");
		
		Calendar c = new GregorianCalendar();
		c.set(2001, 2, 24);
		
		student.setDateOfBirth(c.getTime());
		
		Student savedStudent = studentRepository.save(student);
		
		assertThat(savedStudent).isNotNull();
		assertThat(savedStudent.getMiddleName()).isEqualTo(student.getMiddleName());
	}
	
	@Test
	public void addStudentWithListOfCourses() {
		Student student = new Student();
		student.setStudentId(STUDENT_ID);
		student.setFirstName("Olamide");
		student.setLastName("Olojede");
		student.setMiddleName("Christopher");
		
		Calendar c = new GregorianCalendar();
		c.set(2001, 2, 24);
		
		student.setDateOfBirth(c.getTime());
		
		Course course1 = new Course();
		course1.setCourseCode("MEG501");
		course1.setCourseName("Tribology");
		
		Instructor instructor = new Instructor();
		instructor.setFullName("John Doe");
		
		course1.setInstructor(instructor);
		
		student.getCoursesTaken().add(course1);
		
		Student savedStudent = studentRepository.save(student);
		
		assertThat(savedStudent).isNotNull();
		assertThat(savedStudent.getCoursesTaken()).isNotEmpty();
		assertThat(savedStudent.getCoursesTaken().get(0).getCourseCode()).isEqualTo(course1.getCourseCode());
	}

}
