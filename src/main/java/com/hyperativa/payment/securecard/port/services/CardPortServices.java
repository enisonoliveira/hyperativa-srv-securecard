package com.hyperativa.payment.securecard.port.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.application.dto.response.CardResponse;

public interface CardPortServices {
    void saveCard(CardRequest cardRequest);
    void processFile(MultipartFile file) throws IOException;
    CardResponse findByCardNumber(String cardNumber);
}
