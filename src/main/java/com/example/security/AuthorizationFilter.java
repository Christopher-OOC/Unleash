package com.example.security;

import java.io.IOException;
import java.util.Date;

import com.example.demo.CustomApplicationContext;
import com.example.service.UserServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.model.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader("Authorization");

		System.out.println("Test 5");

		if (authorizationHeader == null
				|| !authorizationHeader.startsWith(SecurityConstants.AUTHORIZATION_HEADER_PREFIX)) {
			chain.doFilter(request, response);

			return;
		}

		System.out.println("Test 6");
		UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
		System.out.println("Test 7");
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		
		String actualToken = token.replace(SecurityConstants.AUTHORIZATION_HEADER_PREFIX, "");

		SecretKey secret = new SecretKeySpec(SecurityConstants.TOKEN_SIGINING_SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());

		JwtParser jwtParser = Jwts
				.parser()
					.setSigningKey(secret).build();
		
		Jwt<?, ?> jwt = jwtParser.parse(actualToken);
		Claims payload = (Claims)jwt.getPayload();
		Date expiration = payload.getExpiration();
		String email = (String)payload.get("email");
		
		Date now = new Date();
		
		if (expiration.before(now)) {
			
			return null;
		}
		
		if (email == null) {
			return null;
		}

		UserServiceImpl userService = (UserServiceImpl) CustomApplicationContext.getServiceBean("userServiceImpl");

		User user = userService.findUserByEmail(email);

		if (user == null) {

			return null;
		}
		
		UserPrincipal userPrincipal = new UserPrincipal(user);
		
		return new UsernamePasswordAuthenticationToken(userPrincipal.getUsername(), null, userPrincipal.getAuthorities());
	}
}
