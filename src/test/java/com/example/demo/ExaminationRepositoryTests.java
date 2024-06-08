package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.model.Course;
import com.example.model.ExaminationSession;
import com.example.repository.ExaminationSessionRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class ExaminationRepositoryTests {
	
	@Autowired
	private ExaminationSessionRepository examinationSessionRepository;
	
	@Test
	public void testCreateExaminationSession() {
		Course course = new Course();
		course.setCourseId(1);
//		course.setCourseName("Nigerian History");
//		course.setCourseCode("GNS303");
//		course.setDateCreated(new Date());
		
		ExaminationSession exam = new ExaminationSession();
		exam.setCourse(course);
		exam.setStartTime(new Date(System.currentTimeMillis()));
		exam.setEndTime(new Date(System.currentTimeMillis() + (3600 * 1000)));
		exam.setSessionClosed(false);
		
		ExaminationSession savedExam = examinationSessionRepository.save(exam);
		
		assertThat(savedExam).isNotNull();
		assertThat(savedExam.isSessionClosed()).isEqualTo(exam.isSessionClosed());
		assertThat(savedExam.getCourse().getCourseId()).isEqualTo(course.getCourseId());
	}
	
	@Test
	public void testfindCurrentExaminationSessionOfACourse() {
		int courseId = 30;
		
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("Nigerian History");
		course.setCourseCode("GNS303");
		course.setDateCreated(new Date());
		
		ExaminationSession exam1 = new ExaminationSession();
		exam1.setCourse(course);
		exam1.setStartTime(new Date(System.currentTimeMillis()));
		exam1.setEndTime(new Date(System.currentTimeMillis() + (3600 * 1000)));
		exam1.setSessionClosed(false);
		
		ExaminationSession exam2 = new ExaminationSession();
		exam2.setCourse(course);
		exam2.setStartTime(new Date(System.currentTimeMillis()));
		exam2.setEndTime(new Date(System.currentTimeMillis() + (3600 * 1000)));
		exam2.setSessionClosed(true);
		
		examinationSessionRepository.save(exam1);
		examinationSessionRepository.save(exam2);
		
		ExaminationSession session = examinationSessionRepository.findCurrentExaminationSession(courseId);
		
		assertThat(session).isNotNull();
		assertThat(session.isSessionClosed()).isEqualTo(false);
		
	}

}
