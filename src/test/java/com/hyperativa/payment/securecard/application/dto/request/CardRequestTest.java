package com.hyperativa.payment.securecard.application.dto.request;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        validator = factoryBean.getValidator();
    }

    @Test
    void shouldPassValidationWithValidFields() {
        CardRequest cardRequest = new CardRequest(
                "1234567890123456",
                "John Doe",
                "12/30",
                "123"
        );

        Set<ConstraintViolation<CardRequest>> violations = validator.validate(cardRequest);
        assertTrue(violations.isEmpty(), "Expected no validation violations");
    }

    @Test
    void shouldFailValidationWhenCardNumberIsBlank() {
        CardRequest cardRequest = new CardRequest(
                "",
                "John Doe",
                "12/30",
                "123"
        );

        Set<ConstraintViolation<CardRequest>> violations = validator.validate(cardRequest);
        assertEquals(1, violations.size(), "Expected one validation violation");
        assertEquals("Card number is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailValidationWhenHolderNameIsBlank() {
        CardRequest cardRequest = new CardRequest(
                "1234567890123456",
                "",
                "12/30",
                "123"
        );

        Set<ConstraintViolation<CardRequest>> violations = validator.validate(cardRequest);
        assertEquals(1, violations.size(), "Expected one validation violation");
        assertEquals("Holder name is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailValidationWhenExpirationDateIsBlank() {
        CardRequest cardRequest = new CardRequest(
                "1234567890123456",
                "John Doe",
                "",
                "123"
        );

        Set<ConstraintViolation<CardRequest>> violations = validator.validate(cardRequest);
        assertEquals(1, violations.size(), "Expected one validation violation");
        assertEquals("Expiration date is required", violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailValidationWhenCvvIsBlank() {
        CardRequest cardRequest = new CardRequest(
                "1234567890123456",
                "John Doe",
                "12/30",
                ""
        );

        Set<ConstraintViolation<CardRequest>> violations = validator.validate(cardRequest);
        assertEquals(1, violations.size(), "Expected one validation violation");
        assertEquals("CVV is required", violations.iterator().next().getMessage());
    }
}