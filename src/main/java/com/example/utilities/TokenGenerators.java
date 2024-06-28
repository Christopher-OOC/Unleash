package com.example.utilities;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.example.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenGenerators {

	public static String generateEmailVerificationToken(String email) {

		byte[] secretBytes = SecurityConstants.TOKEN_SIGINING_SECRET.getBytes();

		SecretKey key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());

		Instant now = Instant.now();

		String emailToken = Jwts.builder().signWith(key, SignatureAlgorithm.HS512).claim("email", email)
				.issuedAt(new Date())
				.expiration(Date.from(now.plusSeconds(SecurityConstants.EMAIL_TOKEN_EXPIRATION_TIME))).compact();

		return emailToken;
	}

	public static boolean hasEmailTokenValid(String emailToken) {

		byte[] secretBytes = SecurityConstants.TOKEN_SIGINING_SECRET.getBytes();

		SecretKey key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());

		JwtParser jwtParser = Jwts
			.parser()
				.setSigningKey(key)
				.build();
		
		Date now = new Date();
		
		Jwt<?, ?> jwt = jwtParser.parse(emailToken);
		Claims payload = (Claims)jwt.getPayload();
		Date expiration = payload.getExpiration();
		
		if (expiration.before(now)) {
			return false;
		}
		
		return true;
	}
	
	public static String generatePasswordVerificationToken(String email) {

		byte[] secretBytes = SecurityConstants.TOKEN_SIGINING_SECRET.getBytes();

		SecretKey key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());

		Instant now = Instant.now();

		String emailToken = Jwts.builder().signWith(key, SignatureAlgorithm.HS512).claim("email", email)
				.issuedAt(new Date())
				.expiration(Date.from(now.plusSeconds(SecurityConstants.EMAIL_TOKEN_EXPIRATION_TIME))).compact();

		return emailToken;
	}
	
	public static boolean hasPasswordTokenValid(String emailToken) {

		byte[] secretBytes = SecurityConstants.TOKEN_SIGINING_SECRET.getBytes();

		SecretKey key = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS512.getJcaName());

		JwtParser jwtParser = Jwts
			.parser()
				.setSigningKey(key)
				.build();
		
		Date now = new Date();
		
		Jwt<?, ?> jwt = jwtParser.parse(emailToken);
		Claims payload = (Claims)jwt.getPayload();
		Date expiration = payload.getExpiration();
		
		if (expiration.before(now)) {
			return false;
		}
		
		return true;
	}
}
