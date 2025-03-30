package com.hyperativa.payment.securecard.infrastructure.configuration.web;

import com.hyperativa.payment.securecard.port.services.JwtTokenPortServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebAuthenticationFilterTest {

    @Mock
    private JwtTokenPortServices jwtTokenProvider;

    @Mock
    private Authentication mockAuthentication;

    @Mock
    private FilterChain mockFilterChain;

    @InjectMocks
    private WebAuthenticationFilter webAuthenticationFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext(); // Clear the security context before each test
    }

    @Test
    void shouldAuthenticateAndSetSecurityContextWhenTokenIsValid() throws ServletException, IOException {
        // Arrange: Mock a valid token
        String validToken = "validJwtToken";
        request.addHeader("Authorization", "Bearer " + validToken);
        when(jwtTokenProvider.validateToken(validToken)).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(validToken)).thenReturn(mockAuthentication);

        // Act: Execute the filter
        webAuthenticationFilter.doFilterInternal(request, response, mockFilterChain);

        // Assert: Verify the SecurityContext is set
        assertEquals(mockAuthentication, SecurityContextHolder.getContext().getAuthentication(),
                "Authentication should be set in the SecurityContext");

        // Verify the filter chain was called
        verify(mockFilterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenTokenIsInvalid() throws ServletException, IOException {
        // Arrange: Mock an invalid token
        String invalidToken = "invalidJwtToken";
        request.addHeader("Authorization", "Bearer " + invalidToken);
        when(jwtTokenProvider.validateToken(invalidToken)).thenReturn(false);

        // Act: Execute the filter
        webAuthenticationFilter.doFilterInternal(request, response, mockFilterChain);

        // Assert: Verify the SecurityContext is not set
        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Authentication should not be set in the SecurityContext");

        // Verify the filter chain was called
        verify(mockFilterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenAuthHeaderIsMissing() throws ServletException, IOException {
        // Act: Execute the filter without an Authorization header
        webAuthenticationFilter.doFilterInternal(request, response, mockFilterChain);

        // Assert: Verify the SecurityContext is not set
        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Authentication should not be set in the SecurityContext");

        // Verify the filter chain was called
        verify(mockFilterChain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldNotAuthenticateWhenAuthHeaderIsInvalidFormat() throws ServletException, IOException {
        // Arrange: Add an invalid Authorization header
        request.addHeader("Authorization", "InvalidFormatHeader");

        // Act: Execute the filter
        webAuthenticationFilter.doFilterInternal(request, response, mockFilterChain);

        // Assert: Verify the SecurityContext is not set
        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                "Authentication should not be set in the SecurityContext");

        // Verify the filter chain was called
        verify(mockFilterChain, times(1)).doFilter(request, response);
    }
}