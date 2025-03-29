package com.hyperativa.payment.securecard.infrastructure.configuration;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hyperativa.payment.securecard.infrastructure.configuration.adapter.JwtAuthenticationFilterAdpter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilterAdpter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilterAdpter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .antMatchers("/v3/api-docs",
                "/v3/api-docs*/**",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-ui/index.html",
                "/webjars/**").permitAll() // Public endpoints
                .anyRequest().authenticated() // All other endpoints require authentication
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add custom filter
            .csrf(csrf -> csrf.disable()); // Disable CSRF for simplicity

        return http.build();
    }

}