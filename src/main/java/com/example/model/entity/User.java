package com.example.model.entity;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	//6 Digits Pin
	@Column(nullable=false)
	private String pin;
	
	private String emailVerificationToken;
	
	private boolean emailVerificationStatus;
	
	private String passwordResetToken;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="users_roles", 
		joinColumns=@JoinColumn(name="users_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="roles_id", referencedColumnName="id")
	)
	private List<Role> roles = new ArrayList<>();;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
