package com.studenthealthwellness.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder bean for encoding passwords
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF protection for testing purposes (not recommended for production)
            .authorizeRequests()
            .requestMatchers("/api/signup", "/api/signin").permitAll() // Allow unauthenticated access to signup and signin
            .requestMatchers("/api/bookings/**").authenticated() // Require authentication for booking endpoints
            .anyRequest().permitAll(); // Allow all other requests

        return http.build(); // Build and return the security filter chain
    }
}
