package com.xpress.auth.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.xpress.auth.test.security.AppProperties;

@SpringBootApplication
public class AppTestXpressrunApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppTestXpressrunApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SpringApplicationContext SpringApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean
	AppProperties AppProperties() {
		return new AppProperties();
	}

}
