package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Examination;
import com.example.model.ExaminationId;

public interface ExaminationRepository extends JpaRepository<Examination, ExaminationId> {

}
