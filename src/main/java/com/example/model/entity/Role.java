package com.example.model.entity;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
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
@Table(name="roles")
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@ManyToMany(mappedBy="roles", fetch=FetchType.LAZY)
	List<User> users = new ArrayList<>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="roles_authorities",
		joinColumns=@JoinColumn(name="roles_id", referencedColumnName="id"),
		inverseJoinColumns=@JoinColumn(name="authorities_id", referencedColumnName="id")
	)
	private List<Authority> authorities = new ArrayList<>();

	public Role(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
