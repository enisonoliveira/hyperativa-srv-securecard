package com.hyperativa.payment.securecard.application.dto.request;

import javax.validation.constraints.NotBlank;

public class CardRequest {
    
    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Holder name is required")
    private String holderName;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @NotBlank(message = "Expiration date is required")
    private String expirationDate;

    @NotBlank(message = "CVV is required")
    private String cvv;

    public CardRequest() {}

    public CardRequest(String cardNumber, String holderName, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

}
