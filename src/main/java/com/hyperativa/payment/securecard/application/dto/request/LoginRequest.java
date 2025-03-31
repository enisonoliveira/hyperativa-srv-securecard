package com.hyperativa.payment.securecard.application.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;

public class LoginRequest {

    @NotNull(message = "Username is required")
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    // Construtores
    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
