package com.example.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.model.entity.User;
import com.example.repository.UserRepository;

@Configuration
public class WebSecurityConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	protected BCryptPasswordEncoder getPasswordEncode() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected UserDetailsService getUserDetailsService() {
		
		return args -> {
			Optional<User> optional = userRepository.findByEmail(args);
			
			if (optional.isEmpty()) {
				throw new UsernameNotFoundException("No such user with username: " + args);
			}
			
			User userEntity = optional.get();
			
			var user = new org.springframework.security.core.userdetails.User(userEntity.getEmail(), userEntity.getPassword(), Collections.emptyList());
			
			return user;
		};
	}
	

	@Bean
	protected AuthenticationManager getAuthenticationManager(HttpSecurity http) throws Exception {
		
		
		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		
		return builder.build();
	}
	
	@Bean
	protected SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
		
		 http
			.authorizeHttpRequests(request -> request
					//.requestMatchers("/**").permitAll()
					.requestMatchers(HttpMethod.POST  ,SecurityConstants.SIGN_UP_URL).permitAll()
					.requestMatchers(HttpMethod.GET, SecurityConstants.EMAIL_VERIFICATION_URL).permitAll()
					.requestMatchers(HttpMethod.GET, SecurityConstants.PASSWORD_RESET_URL).permitAll()
					);
		
		http.csrf(csrf -> csrf.disable());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		// ADD FILTER
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(getAuthenticationManager(http), userRepository);
		authenticationFilter.setUsernameParameter("email");
		authenticationFilter.setPasswordParameter("passowrd");
		authenticationFilter.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
		
		AuthorizationFilter authorizationFilter = new AuthorizationFilter(getAuthenticationManager(http));
		
		http.addFilter(authenticationFilter);
		http.addFilter(authorizationFilter);
		
		return http.build();
	}
}
