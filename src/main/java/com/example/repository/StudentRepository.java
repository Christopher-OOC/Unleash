package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
