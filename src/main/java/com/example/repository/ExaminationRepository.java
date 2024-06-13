package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.entity.Examination;
import com.example.model.entity.ExaminationId;

public interface ExaminationRepository extends JpaRepository<Examination, ExaminationId> {

}
