package com.hyperativa.payment.securecard.port.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenPortServices {

     public String generateToken(UserDetails authentication)  ;
     public boolean validateToken(String token);
     public Authentication getAuthentication(String token) ;
    
}
