package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(value="com.example.model.entity")
@EnableJpaRepositories(value="com.example.repository")
@ComponentScan(value="com.example.exception.handler")
@ComponentScan(value="com.example.controller")
@ComponentScan(value="com.example.service")
@ComponentScan(value="com.example.utilities")
@ComponentScan(value="com.example.config")
@ComponentScan(value="com.example.security")
@ComponentScan(value="com.example.initializer")

@SpringBootApplication
public class Practice1Application {

	public static void main(String[] args) {
		SpringApplication.run(Practice1Application.class, args);
	}

	@Bean
	CustomApplicationContext getContext() {
		return new CustomApplicationContext();
	}


}
