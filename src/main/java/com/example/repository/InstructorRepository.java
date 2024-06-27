package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
	
	Optional<Instructor> findByInstructorId(String instructorId);
	
	Optional<Instructor> findByEmail(String email);

}
