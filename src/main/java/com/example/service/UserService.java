package com.example.service;

import com.example.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	
	User findUserByEmail(String email);
	
}
