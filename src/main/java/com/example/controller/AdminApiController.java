package com.example.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.dto.AdminDto;
import com.example.model.requestmodel.AdminRequestModel;
import com.example.service.AdminService;

@RestController
@RequestMapping("/v1/admins")
public class AdminApiController {
	
	private AdminService adminService;
	
	private ModelMapper modelMapper;
	
	public AdminApiController(AdminService adminService, ModelMapper modelMapper) {
		this.adminService = adminService;
		this.modelMapper = modelMapper;
	}
	
	@PostMapping
	public ResponseEntity<?> createAdmin(@RequestBody AdminRequestModel requestModel) {
		
		AdminDto adminDto = modelMapper.map(requestModel, AdminDto.class);
		
		//adminService
		
		return null;
		
	}

}
