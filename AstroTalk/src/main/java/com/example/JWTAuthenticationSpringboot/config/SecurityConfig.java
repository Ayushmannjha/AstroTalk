package com.example.JWTAuthenticationSpringboot.config;

import com.example.JWTAuthenticationSpringboot.security.JWTAthenticationEntryPoint;
import com.example.JWTAuthenticationSpringboot.security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JWTAthenticationEntryPoint point;
    @Autowired
    private JWTAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // configuration
        http.csrf(csrf->csrf.disable())
                .cors(cors->cors.disable())
                .authorizeHttpRequests(auth -> auth
	            		 .requestMatchers("/auth/register-user").permitAll() // Allow unauthenticated access to registration endpoint
	            		 .requestMatchers("/auth/register-admin").permitAll()
	            		 .requestMatchers("/auth/register-astro").permitAll()
	            		 .requestMatchers("/auth/login").permitAll() // Allow unauthenticated access to login endpoint
	                     .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict access to admin routes to ADMIN role
	                     .requestMatchers("/user/**").hasRole("USER")
	                     .requestMatchers("/astro/**").hasRole("ASTRO")// Restrict access to user routes to USER role
	                     .anyRequest().authenticated()  // All other routes require authentication
	            )
                        .exceptionHandling(ex->ex.authenticationEntryPoint(point))
                        .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
