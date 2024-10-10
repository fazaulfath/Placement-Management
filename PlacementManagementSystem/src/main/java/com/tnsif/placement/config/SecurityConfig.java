package com.tnsif.placement.config;  // Adjust package according to your structure

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    System.out.println("Security configuration is being applied");
	    http
	        .authorizeHttpRequests(authz -> authz
	            .anyRequest().permitAll()
	        )
	        .csrf().disable();
	    return http.build();
	}

}
