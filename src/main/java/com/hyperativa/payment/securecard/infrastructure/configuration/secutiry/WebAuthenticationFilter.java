package com.hyperativa.payment.securecard.infrastructure.configuration.secutiry;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hyperativa.payment.securecard.port.services.JwtTokenPortServices;
@Component
public class WebAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenPortServices jwtTokenProvider;

    public WebAuthenticationFilter(JwtTokenPortServices jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
    
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); 
            return;
        }
    
        String token = authHeader.substring(7); 
        if (!jwtTokenProvider.validateToken(token)) {
            filterChain.doFilter(request, response); 
            return;
        }
    
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        filterChain.doFilter(request, response); 
    }
    
}
