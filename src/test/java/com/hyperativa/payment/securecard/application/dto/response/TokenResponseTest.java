package com.hyperativa.payment.securecard.application.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenResponseTest {

    @Test
    void shouldCreateTokenResponseWithConstructor() {
        // Create an instance using the constructor
        TokenResponse tokenResponse = new TokenResponse("testToken");

        // Assert that the token is initialized correctly
        assertEquals("testToken", tokenResponse.getToken(), "Expected token to be 'testToken'");
    }

    @Test
    void shouldSetAndGetTokenCorrectly() {
        // Create an instance with an empty or null token initially
        TokenResponse tokenResponse = new TokenResponse(null);

        // Set a new token value
        tokenResponse.setToken("newTestToken");

        // Assert that the token is updated correctly
        assertEquals("newTestToken", tokenResponse.getToken(), "Expected token to be 'newTestToken'");
    }
}