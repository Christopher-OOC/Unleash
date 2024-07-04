package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exceptions.NoResourceFoundException;
import com.example.model.entity.User;
import com.example.model.error.ResourceNotFoundType;
import com.example.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findUserByEmail(String email) {
		
		Optional<User> optional = userRepository.findByEmail(email);
		
		if (optional.isEmpty()) {
			throw new NoResourceFoundException(ResourceNotFoundType.NO_USER);
		}
		
		return optional.get();
	}

}
