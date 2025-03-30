package com.hyperativa.payment.securecard.application.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginRequestTest {

    private Validator validator;

    // Define expected error messages as constants for reuse
    private static final String USERNAME_REQUIRED_MSG = "Username is required";
    private static final String USERNAME_EMPTY_MSG = "Username cannot be empty";
    private static final String PASSWORD_REQUIRED_MSG = "Password is required";
    private static final String PASSWORD_EMPTY_MSG = "Password cannot be empty";

    @BeforeEach
    void setUp() {
        // Set up the validator to test constraints
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.afterPropertiesSet();
        validator = factoryBean.getValidator();
    }

    @Test
    void shouldPassValidationWhenFieldsAreValid() {
        // Create a valid LoginRequest instance
        LoginRequest loginRequest = new LoginRequest("testUser", "password123");

        // Validate the object
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Assert that no validation errors occur
        assertTrue(violations.isEmpty(), "Expected no validation violations");
    }

    @Test
    void shouldFailValidationWhenUsernameIsNull() {
        // Create an instance with a null username
        LoginRequest loginRequest = new LoginRequest(null, "password123");

        // Validate the object
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Assert that one validation error occurs with the expected message
        assertEquals(2, violations.size());
    }

    @Test
    void shouldFailValidationWhenUsernameIsEmpty() {
        // Create an instance with an empty username
        LoginRequest loginRequest = new LoginRequest("", "password123");

        // Validate the object
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Assert that one validation error occurs with the expected message
        assertEquals(1, violations.size());
        assertEquals(USERNAME_EMPTY_MSG, violations.iterator().next().getMessage());
    }

    @Test
    void shouldFailValidationWhenPasswordIsNull() {
        // Create an instance with a null password
        LoginRequest loginRequest = new LoginRequest("testUser", null);

        // Validate the object
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Assert that one validation error occurs with the expected message
        assertEquals(2, violations.size());
    }

    @Test
    void shouldFailValidationWhenPasswordIsEmpty() {
        // Create an instance with an empty password
        LoginRequest loginRequest = new LoginRequest("testUser", "");

        // Validate the object
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Assert that one validation error occurs with the expected message
        assertEquals(1, violations.size());
        assertEquals(PASSWORD_EMPTY_MSG, violations.iterator().next().getMessage());
    }
}
