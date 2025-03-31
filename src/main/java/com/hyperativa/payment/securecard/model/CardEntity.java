package com.hyperativa.payment.securecard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Adjusted the column type to TEXT for cardNumber to allow longer encrypted values
    @Column(nullable = false, unique = true, columnDefinition = "TEXT") 
    private String cardNumber;

    @Column(nullable = false)
    private String holderName;

    @Column(nullable = false)
    private String expirationDate;

     // Adjusted the column type to TEXT for cardNumber to allow longer encrypted values
     @Column(nullable = false, unique = true, columnDefinition = "TEXT") 
    private String cvv;

    // Default constructor
    public CardEntity() {}

    // Constructor with parameters
    public CardEntity(String cardNumber, String holderName, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
