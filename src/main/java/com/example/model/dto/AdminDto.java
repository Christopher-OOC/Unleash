package com.example.model.dto;

import java.util.Date;
import java.util.Objects;

import com.example.model.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDto {

	private long id;

	private String adminId;

	private String email;

	private String firstName;

	private String lastName;

	private String middleName;

	private User user;

	private Date dateAdded;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminDto other = (AdminDto) obj;
		return id == other.id;
	}
}
