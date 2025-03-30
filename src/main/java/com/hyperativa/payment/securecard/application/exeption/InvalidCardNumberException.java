package com.hyperativa.payment.securecard.application.exeption;

public class InvalidCardNumberException extends RuntimeException {
    public InvalidCardNumberException(String message) {
        super(message);
    }
}

