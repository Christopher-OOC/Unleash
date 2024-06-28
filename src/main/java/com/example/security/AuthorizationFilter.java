package com.example.security;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.example.model.entity.User;
import com.example.repository.UserRepository;

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

@Component
public class AuthorizationFilter extends BasicAuthenticationFilter {
	
	private UserRepository userRepository;

	public AuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader == null
				|| !authorizationHeader.startsWith(SecurityConstants.AUTHORIZATION_HEADER_PREFIX)) {
			chain.doFilter(request, response);

			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(authorizationHeader);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		
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
		
		if (expiration.after(now)) {
			return null;
		}
		
		if (email == null) {
			return null;
		}
		
		Optional<User> optional = userRepository.findByEmail(email);
		
		if (optional.isEmpty()) {
			return null;
		}
		
		User currentUser = optional.get();
		
		UserPrincipal userPrincipal = new UserPrincipal(currentUser);
		
		return new UsernamePasswordAuthenticationToken(userPrincipal.getUsername(), null, userPrincipal.getAuthorities());
	}

}
