package com.hyperativa.payment.securecard.application.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CertificateWorkerServiceIntegrationTest {

    @Autowired
    private CertificateWorkerService certificateWorkerService;

    @Test
    void shouldRetrievePrivateKeySuccessfully() {
        RSAPrivateKey privateKey = certificateWorkerService.getPrivateKeyFromString();
        assertNotNull(privateKey, "The private key should not be null");
    }

    @Test
    void shouldRetrievePublicKeySuccessfully() {
        RSAPublicKey publicKey = certificateWorkerService.getPublicKey();
        assertNotNull(publicKey, "The public key should not be null");
    }

    @Test
    void shouldThrowExceptionForInvalidPath() {
        CertificateWorkerService faultyService = new CertificateWorkerService();
        
        // Manually set an invalid key path to simulate the error

        assertThrows(Exception.class, faultyService::getPrivateKeyFromString);
        assertThrows(Exception.class, faultyService::getPublicKey);
    }
}