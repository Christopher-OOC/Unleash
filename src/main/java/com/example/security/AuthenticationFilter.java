package com.example.security;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.example.service.UserServiceImpl;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.CustomApplicationContext;
import com.example.model.entity.User;
import com.example.model.requestmodel.UserLoginRequestModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public AuthenticationFilter(AuthenticationManager manager) {
		super(manager);
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
		
		String email = ((UserPrincipal)authResult.getPrincipal()).getUsername();
		
		String userId = null;

		UserServiceImpl userService = (UserServiceImpl) CustomApplicationContext.getServiceBean("userServiceImpl");

		User user = userService.findUserByEmail(email);

		if (user == null) {
			throw new AccessDeniedException("Access Denied!!!");
		}
		
		userId = user.getPublicUserId();

		System.out.println("Test 3");
		
		String token = Jwts
			.builder()
				.signWith(key, SignatureAlgorithm.HS512)
				.claim("email", email)
				.issuedAt(Date.from(now))
				.expiration(Date.from(now.plusSeconds(SecurityConstants.TOKEN_EXPIRATION_TIME)))
				.compact();

		System.out.println("Test 4");
		
		response.addHeader(SecurityConstants.AUTHORIZATION_HEADER_NAME, SecurityConstants.AUTHORIZATION_HEADER_PREFIX + token);
		response.addHeader("userId", userId);
	} 
}
