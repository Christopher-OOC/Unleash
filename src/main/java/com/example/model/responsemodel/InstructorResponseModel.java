package com.example.model.responsemodel;
import java.util.Date;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonPropertyOrder({"instructorId", "fullName", "email", "dateRegistered"})
public class InstructorResponseModel extends RepresentationModel<InstructorResponseModel> {
	
	@JsonProperty("instructor_id")
	private String instructorId;
	
	@NotNull(message="Last name cannot be null")
	@NotBlank(message="Last name cannot be empty")
	@Length(min=2, max=20, message="Last name must have between 2-20 characters")
	@JsonProperty("full_name")
	private String fullName;
	
	@NotNull(message="Email cannot be null")
	@NotBlank(message="Email cannot be empty")
	@Email(message="Email provided must be a valid email")
	private String email;
	
	@JsonProperty("date_registered")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date dateRegistered;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		InstructorResponseModel other = (InstructorResponseModel) obj;
		return Objects.equals(instructorId, other.instructorId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(instructorId);
		return result;
	}

	@Override
	public String toString() {
		return "InstructorResponseModel [instructorId=" + instructorId + ", fullName=" + fullName + ", email=" + email
				+ ", dateRegistered=" + dateRegistered + "]";
	}

}
