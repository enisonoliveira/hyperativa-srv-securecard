package com.hyperativa.payment.securecard.api.controller;

import com.hyperativa.payment.securecard.port.services.JwtTokenPortServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenPortServices jwtTokenProvider;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void shouldGenerateTokenWhenCredentialsAreValid() throws Exception {

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtTokenProvider.generateToken(userDetails)).thenReturn("generatedToken");

        // Perform the POST request
        mockMvc.perform(post("/api/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("generatedToken"));

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, times(1)).generateToken(any(UserDetails.class));
    }

    @Test
    void shouldReturnUnauthorizedWhenCredentialsAreInvalid() throws Exception {
        // Prepare input and mock behaviors
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        // Perform the POST request
        mockMvc.perform(post("/api/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"password\":\"wrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.token").value("Invalid credentials"));

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, times(0)).generateToken(any(UserDetails.class));
    }

    @Test
    void shouldFailValidationWhenUsernameIsEmpty() throws Exception {
        // Perform the POST request with an empty username
        mockMvc.perform(post("/api/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"\",\"password\":\"password123\"}"))
                .andExpect(status().isBadRequest());

        // Verify no interactions with authenticationManager
        verify(authenticationManager, times(0)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void shouldFailValidationWhenPasswordIsEmpty() throws Exception {
        // Perform the POST request with an empty password
        mockMvc.perform(post("/api/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testUser\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());

        // Verify no interactions with authenticationManager
        verify(authenticationManager, times(0)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
