package com.example.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String authorizationHeader = request.getHeader("Authorization");
		
		if (authorizationHeader == null) {
			chain.doFilter(request, response);
		}
		
		SecretKey secret = new SecretKeySpec(SecurityConstants.TOKEN_SIGINING_SECRET.getBytes(), SIG.HS512.toString());
		
		JwtParser jwtParser = Jwts.parser()
			.setSigningKey(secret)
			.build();
			
		
		
	}
	
	
	

}
