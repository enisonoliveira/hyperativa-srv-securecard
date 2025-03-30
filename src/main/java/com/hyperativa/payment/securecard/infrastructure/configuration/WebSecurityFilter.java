package com.hyperativa.payment.securecard.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityFilter {

    private final WebAuthenticationFilter jwtAuthenticationFilter;

    public WebSecurityFilter(WebAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(auth -> auth
                .antMatchers(
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/swagger-ui/index.html",
                    "/h2-console/**",
                    "/api/token",
                    "/api/validate",
                    "/configuration/security",
                    "/webjars/**"
                ).permitAll() // Public endpoints
                .anyRequest().permitAll() // All other endpoints require authentication
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .headers(headers -> headers
                .frameOptions().sameOrigin() // Permite que a aplicação seja carregada em um iframe da mesma origem
            );

        return http.build();
    }
}
