package com.example.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.model.entity.User;
import com.example.repository.UserRepository;

@Configuration
public class WebSecurityConfig {
	
	@Bean
	protected BCryptPasswordEncoder getPasswordEncode() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {

		AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
		AuthenticationManager manager = builder.build();

		// ADD FILTER
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(manager);
		authenticationFilter.setUsernameParameter("email");
		authenticationFilter.setPasswordParameter("passowrd");
		authenticationFilter.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);

		AuthorizationFilter authorizationFilter = new AuthorizationFilter(manager);


		http
			.authorizeHttpRequests(request -> request
					.requestMatchers(HttpMethod.POST  ,SecurityConstants.STUDENT_SIGN_UP_URL).permitAll()
					.requestMatchers(HttpMethod.POST  ,SecurityConstants.INSTRUCTOR_SIGN_UP_URL).permitAll()
					.requestMatchers(HttpMethod.GET, SecurityConstants.EMAIL_VERIFICATION_URL).permitAll()
					.requestMatchers(HttpMethod.GET, SecurityConstants.PASSWORD_RESET_URL).permitAll()
					.anyRequest().authenticated()
					)
				.addFilter(authenticationFilter)
				.addFilter(authorizationFilter)
				.authenticationManager(manager)
				.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.getOrBuild();
	}
}
