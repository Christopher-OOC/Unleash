package com.example.service;

import java.util.Optional;

import com.example.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optional = userRepository.findByEmail(username);

		if (optional.isEmpty()) {
			throw new UsernameNotFoundException("No such user with username: " + username);
		}

		User userEntity = optional.get();

		return new UserPrincipal(userEntity);
	}
}
