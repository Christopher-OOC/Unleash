package com.example.model.dto;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import com.example.model.entity.Course;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionDto {
	
	private long id;
	
	private String questionId;
	
	private String question;
	
	private Course course;
	
	private List<QuestionOptionDto> options = new ArrayList<>();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionDto other = (QuestionDto) obj;
		return questionId == other.questionId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(questionId);
	}

	

}
