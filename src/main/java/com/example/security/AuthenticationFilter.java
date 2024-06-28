package com.example.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.example.config.CustomApplicationContext;
import com.example.model.dto.InstructorDto;
import com.example.model.dto.StudentDto;
import com.example.model.entity.User;
import com.example.model.entity.UserType;
import com.example.model.requestmodel.UserLoginRequestModel;
import com.example.repository.UserRepository;
import com.example.service.InstructorService;
import com.example.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UserRepository userRepository;
	
	public AuthenticationFilter(AuthenticationManager manager, UserRepository userRepository) {
		super(manager);
		this.userRepository = userRepository;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		UserLoginRequestModel creds = null;
		
		try {
			creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
	
		} 
		catch (IOException e) {
	
			e.printStackTrace();
		}
		
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), null));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		byte[] byteTokenSecret = SecurityConstants.TOKEN_SIGINING_SECRET.getBytes();
		
		SecretKey key = new SecretKeySpec(byteTokenSecret, SignatureAlgorithm.HS512.getJcaName());
		
		Instant now = Instant.now();
		
		String email = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
		
		String userId = null;
		
		Optional<User> optional = userRepository.findByEmail(email);
		
		if (optional.isEmpty()) {
			throw new AccessDeniedException("Access Denied!!!");
		}
		
		User currentUser = optional.get();
		
		if (currentUser.getUserType() == UserType.INSTRUCTOR) {
			InstructorDto instructorDto = ((InstructorService) CustomApplicationContext.getServiceBean("instructorServiceImpl")).getInstructorByEmail(email);
			
			userId = instructorDto.getInstructorId();
		}
		else if (currentUser.getUserType() == UserType.STUDENT) {
			StudentDto studentDto = ((StudentService) CustomApplicationContext.getServiceBean("studentServiceImpl")).getStudentByEmail(email);
			
			userId = studentDto.getStudentId();
		}
		
		
		
		String token = Jwts
			.builder()
				.signWith(key, SignatureAlgorithm.HS512)
				.claim("email", email)
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plusSeconds(SecurityConstants.TOKEN_EXPIRATION_TIME)))
				.compact();
		
		response.addHeader(SecurityConstants.AUTHORIZATION_HEADER_NAME, SecurityConstants.AUTHORIZATION_HEADER_PREFIX + token);
		response.addHeader("userId", userId);
	}
	
	

}
