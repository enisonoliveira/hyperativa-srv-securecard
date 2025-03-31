package com.hyperativa.payment.securecard.application.exeption;

public class InvalidCardCVCException extends RuntimeException {
    public InvalidCardCVCException(String message) {
        super(message);
    }
}

