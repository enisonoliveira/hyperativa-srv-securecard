package com.hyperativa.payment.securecard.infrastructure.adapter.secutiry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.hyperativa.payment.securecard.application.service.CertificateWorkerService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecureteJwtTokeAdapterIntegrationTest {

    private SecureteJwtTokeAdapter secureteJwtTokeAdapter;

    @Autowired
    private CertificateWorkerService certificateWorkerService ;

    @BeforeEach
    void setUp() {
        // Initialize SecureteJwtTokeAdapter with a real CertificateWorkerService
      
        secureteJwtTokeAdapter = new SecureteJwtTokeAdapter(certificateWorkerService);
    }

    @Test
    void shouldGenerateTokenSuccessfully() {
        // Create a UserDetails object
        UserDetails userDetails = User.builder()
                .username("testUser")
                .password("password123")
                .authorities("ROLE_USER")
                .build();

        // Generate token
        String token = secureteJwtTokeAdapter.generateToken(userDetails);

        // Assert token is generated successfully
        assertNotNull(token, "Generated token should not be null");

        // Validate the token
        assertTrue(secureteJwtTokeAdapter.validateToken(token), "Token should be valid");

        // Extract authentication
        Authentication authentication = secureteJwtTokeAdapter.getAuthentication(token);
        assertEquals("testUser", authentication.getPrincipal(), "Authentication principal should match username");
    }

    @Test
    void shouldValidateTokenSuccessfully() {
        // Generate token using an actual private key
        UserDetails userDetails = User.builder()
                .username("testUser")
                .password("password123")
                .authorities("ROLE_USER")
                .build();

        String token = secureteJwtTokeAdapter.generateToken(userDetails);

        // Validate the token using the real public key
        boolean isValid = secureteJwtTokeAdapter.validateToken(token);

        // Assert that the token is valid
        assertTrue(isValid, "Token should be valid");
    }

    @Test
    void shouldExtractAuthenticationSuccessfully() {
        // Generate token using actual private key
        UserDetails userDetails = User.builder()
                .username("testUser")
                .password("password123")
                .authorities("ROLE_USER")
                .build();

        String token = secureteJwtTokeAdapter.generateToken(userDetails);

        // Extract authentication using the real public key
        Authentication authentication = secureteJwtTokeAdapter.getAuthentication(token);

        // Assert that the authentication is extracted successfully
        assertNotNull(authentication, "Authentication object should not be null");
        assertEquals("testUser", authentication.getPrincipal(), "Authentication principal should match username");
    }
}