package com.hyperativa.payment.securecard.api.based;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyperativa.payment.securecard.application.dto.request.LoginRequest;
import com.hyperativa.payment.securecard.application.dto.response.TokenResponse;

@RequestMapping("/api")
public interface AuthBased {

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> generateToken(@Valid @RequestBody LoginRequest loginRequest);
}
