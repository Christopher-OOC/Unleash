package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>, FilterStudentRepository {
	
	Optional<Student> findByStudentId(String studentId);

}
