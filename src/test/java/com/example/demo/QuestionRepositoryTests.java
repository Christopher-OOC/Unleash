/*
package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.model.entity.Course;
import com.example.model.entity.Instructor;
import com.example.model.entity.Question;
import com.example.model.entity.QuestionOption;
import com.example.repository.CourseRepository;
import com.example.repository.QuestionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class QuestionRepositoryTests {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Value("${examination.api.numberOfExaminationQuestion}")
	private int numberOfExaminationQuestion;
	
	@Test
	public void testAddQuestionWithOptions() {
		Question question1 = new Question();
		question1.setQuestion("Which school is the best in Nigeria?");
		
		Instructor instructor = new Instructor();
		instructor.setFullName("Korex Korex");
		
		Course course1 = new Course();
		course1.setCourseId(15);
		course1.setCourseCode("GNS101");
		course1.setCourseName("General Studies");
		
		course1.setInstructor(instructor);
		
		question1.setCourse(course1);
		
		
		QuestionOption option1 = new QuestionOption();
		option1.setQuestion(question1);
		option1.setOptionValue("OAU");
		option1.setCorrect(false);
		
		QuestionOption option2 = new QuestionOption();
		option2.setQuestion(question1);
		option2.setOptionValue("OOU");
		option2.setCorrect(true);
		
		QuestionOption option3 = new QuestionOption();
		option3.setQuestion(question1);
		option3.setOptionValue("LASU");
		option3.setCorrect(false);
		
		QuestionOption option4 = new QuestionOption();
		option4.setQuestion(question1);
		option4.setOptionValue("UNILAG");
		option4.setCorrect(false);
		
		question1.setOptions(List.of(option1, option2, option3, option4));
		
		Question savedQuestion = questionRepository.save(question1);
		
		
		
		assertThat(savedQuestion).isNotNull();
		assertThat(savedQuestion.getOptions()).isNotEmpty();
	}
	
	@Test
	public void testGetExaminationQuestionWithLimit() {
		Question question1 = new Question();
		question1.setQuestion("Which school is the best in Nigeria?");
		
		Instructor instructor = new Instructor();
		instructor.setFullName("Korex Korex");
		
		Course course1 = new Course();
		course1.setCourseCode("GNS101");
		course1.setCourseName("General Studies");
		
		course1.setInstructor(instructor);
		
		Course course = courseRepository.save(course1);
		
		question1.setCourse(course);
		
		
		QuestionOption option1 = new QuestionOption();
		option1.setQuestion(question1);
		option1.setOptionValue("OAU");
		option1.setCorrect(false);
		
		QuestionOption option2 = new QuestionOption();
		option2.setQuestion(question1);
		option2.setOptionValue("OOU");
		option2.setCorrect(true);
		
		QuestionOption option3 = new QuestionOption();
		option3.setQuestion(question1);
		option3.setOptionValue("LASU");
		option3.setCorrect(false);
		
		QuestionOption option4 = new QuestionOption();
		option4.setQuestion(question1);
		option4.setOptionValue("UNILAG");
		option4.setCorrect(false);
		
		question1.setOptions(List.of(option1, option2, option3, option4));
		
		questionRepository.save(question1);
		
		
		// QUESTION2
		Question question2 = new Question();
		question2.setQuestion("Which school is the best in Nigeria?");
		
		
		
		question2.setCourse(course);
		
		
		QuestionOption option12 = new QuestionOption();
		option12.setQuestion(question2);
		option12.setOptionValue("OAU");
		option12.setCorrect(false);
		
		QuestionOption option22 = new QuestionOption();
		option22.setQuestion(question2);
		option22.setOptionValue("OOU");
		option22.setCorrect(true);
		
		QuestionOption option32 = new QuestionOption();
		option32.setQuestion(question2);
		option32.setOptionValue("LASU");
		option32.setCorrect(false);
		
		QuestionOption option42 = new QuestionOption();
		option42.setQuestion(question2);
		option42.setOptionValue("UNILAG");
		option42.setCorrect(false);
		
		question2.setOptions(List.of(option12, option22, option32, option42));
		
		questionRepository.save(question2);
		
		// QUESTION3
		Question question3 = new Question();
		question3.setQuestion("Which school is the best in Nigeria?");
		
		
		
		question3.setCourse(course);
		
		
		QuestionOption option13 = new QuestionOption();
		option13.setQuestion(question3);
		option13.setOptionValue("OAU");
		option13.setCorrect(false);
		
		QuestionOption option23 = new QuestionOption();
		option23.setQuestion(question3);
		option23.setOptionValue("OOU");
		option23.setCorrect(true);
		
		QuestionOption option33 = new QuestionOption();
		option33.setQuestion(question3);
		option33.setOptionValue("LASU");
		option33.setCorrect(false);
		
		QuestionOption option43 = new QuestionOption();
		option43.setQuestion(question3);
		option43.setOptionValue("UNILAG");
		option43.setCorrect(false);
		
		question2.setOptions(List.of(option13, option23, option33, option43));
		
		questionRepository.save(question3);
		
		List<Question> limitExamQuestions = questionRepository.getExaminationQuestions(course.getCourseId(), 2);
		
		log.info("Limit Questions: {}", limitExamQuestions.size());
		
		assertThat(limitExamQuestions.size()).isEqualTo(2);
	}
	
	@Test
	public void testNumberOfExaminationQuestions() {
		System.out.println(numberOfExaminationQuestion);
		log.info("numberOfExaminationQuestion: {}", numberOfExaminationQuestion);
	}
	

}
*/