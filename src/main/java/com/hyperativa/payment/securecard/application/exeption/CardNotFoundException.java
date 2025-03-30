package com.hyperativa.payment.securecard.application.exeption;


public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException() {
        super("Card not found");
    }

    public CardNotFoundException(String message) {
        super(message);
    }

    public CardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CardNotFoundException(Throwable cause) {
        super(cause);
    }
}
