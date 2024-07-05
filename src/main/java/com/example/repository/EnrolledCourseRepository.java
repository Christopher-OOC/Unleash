package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.entity.EnrolledCourse;
import com.example.model.entity.EnrolledCourseId;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, EnrolledCourseId> {

}
