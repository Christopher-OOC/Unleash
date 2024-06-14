package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.model.entity.Student;

public interface FilterStudentRepository {
	Page<Student> getAllStudentsByPageAndSearch(Pageable pageable, String search);
}
