package com.hyperativa.payment.securecard.application.dto.response;


public class CardResponse {
    
    private Long id;


   

    public CardResponse() {}

    public CardResponse( Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
