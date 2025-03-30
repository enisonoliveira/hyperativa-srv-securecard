package com.hyperativa.payment.securecard.api.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyperativa.payment.securecard.api.based.AuthBased;
import com.hyperativa.payment.securecard.application.dto.request.LoginRequest;
import com.hyperativa.payment.securecard.application.dto.response.TokenResponse;
import com.hyperativa.payment.securecard.port.services.JwtTokenPortServices;

@RestController
public class AuthController implements  AuthBased{

    private final AuthenticationManager authenticationManager;
    private final JwtTokenPortServices jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenPortServices jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public ResponseEntity<TokenResponse> generateToken(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtTokenProvider.generateToken(userDetails);

            return ResponseEntity.ok(new TokenResponse(token)); 

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(new TokenResponse(e.getMessage()));
        }
    }
}
