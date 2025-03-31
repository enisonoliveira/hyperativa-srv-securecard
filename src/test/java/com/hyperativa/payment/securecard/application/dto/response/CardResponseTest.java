package com.hyperativa.payment.securecard.application.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardResponseTest {

    @Test
    void shouldCreateCardResponseWithDefaultConstructor() {
        // Create an instance using the default constructor
        CardResponse cardResponse = new CardResponse();

        // Verify that the id is null
        assertNull(cardResponse.getId(), "Expected id to be null for default constructor");
    }

    @Test
    void shouldCreateCardResponseWithParameterizedConstructor() {
        // Create an instance using the parameterized constructor
        CardResponse cardResponse = new CardResponse(123L);

        // Verify that the id is set correctly
        assertEquals(123L, cardResponse.getId(), "Expected id to be 123");
    }

    @Test
    void shouldSetIdCorrectly() {
        // Create an instance and set the id
        CardResponse cardResponse = new CardResponse();
        cardResponse.setId(456L);

        // Verify that the id is updated correctly
        assertEquals(456L, cardResponse.getId(), "Expected id to be 456 after setId");
    }
}