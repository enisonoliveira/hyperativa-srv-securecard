package com.hyperativa.payment.securecard.infrastructure.configuration.secutiry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityFilter {

    private final WebAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public WebSecurityFilter(WebAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
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
                .anyRequest().authenticated() // All other endpoints require authentication
            )
           .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Use JwtAuthenticationEntryPoint here
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .headers(headers -> headers
                .frameOptions().sameOrigin() // Permite que a aplicação seja carregada em um iframe da mesma origem
            );

        return http.build();
    }
}
