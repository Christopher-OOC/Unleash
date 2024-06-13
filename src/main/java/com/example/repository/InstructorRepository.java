package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

}
