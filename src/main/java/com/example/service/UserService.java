package com.example.service;

import com.example.model.entity.User;

public interface UserService {
	
	User findUserByEmail(String email);
	
}
