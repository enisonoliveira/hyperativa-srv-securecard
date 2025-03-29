package com.hyperativa.payment.securecard.infrastructure.configuration.adapter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.hyperativa.payment.securecard.infrastructure.configuration.port.JwtTokenProvider;
import com.hyperativa.payment.securecard.infrastructure.configuration.service.CertificateWorkerService;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
public class JwtTokenProviderAdapter implements  JwtTokenProvider {

    private final CertificateWorkerService certificateWorkerService;
    static final long JWT_EXPIRATION_TIME = 3600000; // 1 hour

    public JwtTokenProviderAdapter(CertificateWorkerService certificateWorkerService) {
        this.certificateWorkerService = certificateWorkerService;
    }

    // Generate a JWT Token using the private key
    @Override
    public String generateToken(Authentication authentication) throws Exception {
        User user = (User) authentication.getPrincipal();
        PrivateKey privateKey = this.certificateWorkerService.getPrivateKeyFromString();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(privateKey, SignatureAlgorithm.RS256) // Sign with private key
                .compact();
    }

    // Validate a JWT Token using the public key
    @Override
    public boolean validateToken(String token) {

        PublicKey publicKey = certificateWorkerService.getPublicKey();
        Jwts.parserBuilder()
            .setSigningKey(publicKey) // Validate with public key
            .build()
            .parseClaimsJws(token);
        return true;
    }

    // Extract authentication details from the token
    @Override
    public Authentication getAuthentication(String token)  {
        PublicKey publicKey = certificateWorkerService.getPublicKey();
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey) // Validate with public key
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return new UsernamePasswordAuthenticationToken(username, null, null); // Authorities can be added here
    }
}