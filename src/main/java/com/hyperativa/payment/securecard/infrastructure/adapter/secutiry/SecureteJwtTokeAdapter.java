package com.hyperativa.payment.securecard.infrastructure.adapter.secutiry;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hyperativa.payment.securecard.port.services.CertificateWorkerPortServices;
import com.hyperativa.payment.securecard.port.services.JwtTokenPortServices;
import com.hyperativa.payment.securecard.application.exeption.MalformedJwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class SecureteJwtTokeAdapter implements JwtTokenPortServices {

    private final CertificateWorkerPortServices certificateWorkerService;
    static final long JWT_EXPIRATION_TIME = 3600000; // 1 hour

    public SecureteJwtTokeAdapter(CertificateWorkerPortServices certificateWorkerService) {
        this.certificateWorkerService = certificateWorkerService;
    }

    // Generate a JWT Token using the private key
    @Override
    public String generateToken(UserDetails user) {
        try {
            PrivateKey privateKey = this.certificateWorkerService.getPrivateKeyFromString();
            if (privateKey == null) {
                throw new IllegalStateException("Private key is not available.");
            }

            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                    .signWith(privateKey, SignatureAlgorithm.RS256) // Sign with private key
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token: " + e.getMessage(), e);
        }
    }

    // Validate a JWT Token using the public key
    @Override
    public boolean validateToken(String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                throw new IllegalArgumentException("JWT token is null or empty.");
            }

            // Token should contain exactly two periods
            if (token.split("\\.").length != 3) {
                throw new MalformedJwtException("JWT string must contain exactly 2 period characters. Found: " + (token.split("\\.").length - 1));
            }

            PublicKey publicKey = certificateWorkerService.getPublicKey();
            if (publicKey == null) {
                throw new IllegalStateException("Public key is not available.");
            }

            Jwts.parserBuilder()
                .setSigningKey(publicKey) // Validate with public key
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            throw e; // Rethrow the MalformedJwtException to be handled globally
        } catch (Exception e) {
            throw new RuntimeException("Error validating JWT token: " + e.getMessage(), e);
        }
    }

    // Extract authentication details from the token
    @Override
    public Authentication getAuthentication(String token) {
        try {
            PublicKey publicKey = certificateWorkerService.getPublicKey();
            if (publicKey == null) {
                throw new IllegalStateException("Public key is not available.");
            }

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey) // Validate with public key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            return new UsernamePasswordAuthenticationToken(username, null, null); // Authorities can be added here
        } catch (Exception e) {
            throw new RuntimeException("Error extracting authentication from JWT token: " + e.getMessage(), e);
        }
    }
}
