package com.hyperativa.payment.securecard.api.dto.response;

public class TokenResponse {
    private String token;

    // Construtores
    public TokenResponse(String token) {
        this.token = token;
    }

    // Getter e Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
