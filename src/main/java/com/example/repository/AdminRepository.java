package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
