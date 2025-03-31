package com.hyperativa.payment.securecard.application.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hyperativa.payment.securecard.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hyperativa.payment.securecard.port.repository.UserPort;

import java.util.Optional;

class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserPort userPort;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a dummy user entity
        user = new UserEntity("testUser","testPassword");
    }

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        // Mock behavior: when findByUsername is called with "testUser", return a valid UserEntity
        when(userPort.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Call the method
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");

        // Assert that the userDetails are correct
        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("testPassword", userDetails.getPassword());
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserNotFound() {
        // Mock behavior: when findByUsername is called with "nonExistentUser", return an empty Optional
        when(userPort.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Call the method and assert that it throws UsernameNotFoundException
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("nonExistentUser");
        });
    }
}
