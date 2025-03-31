package com.hyperativa.payment.securecard.infrastructure.configuration.webfilters;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hyperativa.payment.securecard.port.services.JwtTokenPortServices;

@Component
public class WebAuthenticationFilter extends OncePerRequestFilter{

    private final JwtTokenPortServices jwtTokenProvider;

    public WebAuthenticationFilter(JwtTokenPortServices jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
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