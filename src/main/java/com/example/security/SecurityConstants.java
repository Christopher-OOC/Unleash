package com.example.security;

public class SecurityConstants {
	
	public static final String STUDENT_SIGN_UP_URL = "/v1/students";
	
	public static final String INSTRUCTOR_SIGN_UP_URL = "/v1/instructors";
	
	public static final String LOGIN_URL = "/v1/login";
	
	public static final String EMAIL_VERIFICATION_URL = "/email-verification";
	
	public static final String PASSWORD_RESET_URL = "/password-reset";
	
	public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
	
	public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
	
	public static final String TOKEN_SIGINING_SECRET = "poSuyrFD2hjDmnGFDn37G2n29j2GFkGlHl2HF3kjdskjkn.njkd.kjnkmfjksd.hiygyugiiunlj";
	
	public static final long TOKEN_EXPIRATION_TIME = 600000;
	
	public static final long EMAIL_TOKEN_EXPIRATION_TIME = 7200;
	
}
