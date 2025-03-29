package com.hyperativa.payment.securecard.infrastructure.configuration.port;

import org.springframework.security.core.Authentication;

public interface JwtTokenProvider {

      public String generateToken(Authentication authentication) throws Exception;
     public boolean validateToken(String token);
     public Authentication getAuthentication(String token) ;
    
}
