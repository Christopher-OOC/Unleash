package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	
	Optional<Authority> findByName(String name);

}
