package com.hyperativa.payment.securecard.infrastructure.configuration.secutiry;

import com.hyperativa.payment.securecard.application.service.CustomUserDetailsService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class SecurityBeansConfigTest {

    @Autowired
    private SecurityBeansConfig securityBeansConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void shouldLoadSecurityBeansConfig() {
        // Verify the SecurityBeansConfig is loaded
        assertNotNull(securityBeansConfig, "SecurityBeansConfig should not be null");
    }

    @Test
    void shouldCreateAuthenticationManagerBean() {
        // Verify the AuthenticationManager bean is created
        assertNotNull(authenticationManager, "AuthenticationManager should not be null");
    }

    @Test
    void shouldCreatePasswordEncoderBean() {
        // Verify the PasswordEncoder bean is created
        assertNotNull(passwordEncoder, "PasswordEncoder should not be null");
    }

    @Test
    void authenticationManagerShouldUseCustomUserDetailsService() {
        // Verify that the custom user details service is used
        assertNotNull(customUserDetailsService, "CustomUserDetailsService should not be null");
    }

    @Test
    void passwordEncoderShouldBeBCryptPasswordEncoder() {
        // Verify that the PasswordEncoder is an instance of BCryptPasswordEncoder
        assertNotNull(passwordEncoder, "PasswordEncoder should be a BCryptPasswordEncoder");
    }
}