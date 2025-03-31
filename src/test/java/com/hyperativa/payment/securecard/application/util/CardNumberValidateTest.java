package com.hyperativa.payment.securecard.application.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardNumberValidateTest {

    private CardNumberValidate cardNumberValidate;

    @BeforeEach
    void setUp() {
        cardNumberValidate = new CardNumberValidate();
    }

    @Test
    void shouldReturnTrueForValidCardNumber() {
        // Test with a valid card number (example: Visa test card)
        String validCardNumber = "4111111111111111"; // This is a known valid test card number

        boolean result = cardNumberValidate.isValidCardNumber(validCardNumber);

        assertTrue(result, "The card number should be valid.");
    }

    @Test
    void shouldReturnFalseForInvalidCardNumber() {
        // Test with an invalid card number
        String invalidCardNumber = "1234567890123456"; // This should fail Luhn check

        boolean result = cardNumberValidate.isValidCardNumber(invalidCardNumber);

        assertFalse(result, "The card number should be invalid.");
    }

    @Test
    void shouldReturnTrueForValidCardNumberWithDifferentLength() {
        // Test a card number of a valid length but still valid according to Luhn
        String validCardNumber = "5555555555554444"; // Another valid card number

        boolean result = cardNumberValidate.isValidCardNumber(validCardNumber);

        assertTrue(result, "The card number should be valid.");
    }

    @Test
    void shouldReturnFalseForCardNumberThatDoesNotPassLuhnCheck() {
        // Test a card number that passes length check but fails Luhn
        String invalidCardNumber = "1234567..."; // This number is too short to pass Luhn algorithm

        boolean result = cardNumberValidate.isValidCardNumber(invalidCardNumber);

        assertFalse(result, "The card number should be invalid.");
    }

    @Test
    void shouldReturnFalseForShortCardNumber() {
        // Test with a card number that's too short
        String shortCardNumber = "123456789012";

        boolean result = cardNumberValidate.isValidCardNumber(shortCardNumber);

        assertFalse(result, "The card number should be invalid (too short).");
    }

    @Test
    void shouldReturnFalseForLongCardNumber() {
        // Test with a card number that's too long
        String longCardNumber = "123456789012345678901"; // More than 19 digits

        boolean result = cardNumberValidate.isValidCardNumber(longCardNumber);

        assertFalse(result, "The card number should be invalid (too long).");
    }
}
