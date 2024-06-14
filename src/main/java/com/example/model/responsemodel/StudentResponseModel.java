package com.example.model.responsemodel;

import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentResponseModel extends RepresentationModel<StudentResponseModel> {
	
	private String studentId;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private String email;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date dateOfBirth;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentResponseModel other = (StudentResponseModel) obj;
		return studentId == other.studentId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId);
	}
	
	
}
