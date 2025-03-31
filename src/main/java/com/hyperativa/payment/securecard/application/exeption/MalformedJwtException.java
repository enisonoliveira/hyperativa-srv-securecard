package com.hyperativa.payment.securecard.application.exeption;

public class MalformedJwtException extends RuntimeException {

    public MalformedJwtException(String message) {
        super(message);
    }
}
