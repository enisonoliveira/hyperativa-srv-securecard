package com.hyperativa.payment.securecard.infrastructure.configuration.adapter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hyperativa.payment.securecard.infrastructure.configuration.port.JwtTokenProvider;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationFilterAdpter extends OncePerRequestFilter{

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilterAdpter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        Authentication authentication ;
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            
            String token = authHeader.substring(7);

            if (jwtTokenProvider.validateToken(token)) {
                authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}