package com.hyperativa.payment.securecard.infrastructure.port;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenProvider {

     public String generateToken(UserDetails authentication)  ;
     public boolean validateToken(String token);
     public Authentication getAuthentication(String token) ;
    
}
