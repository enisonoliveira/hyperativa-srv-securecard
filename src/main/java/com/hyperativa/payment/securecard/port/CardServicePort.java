package com.hyperativa.payment.securecard.port;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;

public interface CardServicePort {
    void saveCard(CardRequest cardRequest);
    void processFile(MultipartFile file) throws IOException;
}
