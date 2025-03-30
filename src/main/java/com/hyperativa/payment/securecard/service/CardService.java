package com.hyperativa.payment.securecard.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.infrastructure.adapter.CardAdapter;
import com.hyperativa.payment.securecard.model.CardEntity;
import com.hyperativa.payment.securecard.port.CardPort;
import com.hyperativa.payment.securecard.port.CardServicePort;

@Service
public class CardService implements CardServicePort{

    private final CardAdapter cardPort;

    public CardService(CardAdapter cardPort) {
        this.cardPort = cardPort;
    }

    // 1️⃣ Método para salvar um único cartão no banco de dados
    public void saveCard(CardRequest cardRequest) {
        CardEntity card = new CardEntity(
            cardRequest.getCardNumber(),
            cardRequest.getHolderName(),
            cardRequest.getExpirationDate(),
            cardRequest.getCvv()
        );
        cardPort.save(card);
    }

    // 2️⃣ Método para processar o arquivo TXT
    public void processFile(MultipartFile file) throws IOException {
        List<CardEntity> cards = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    CardEntity card = new CardEntity(parts[0], parts[1], parts[2], parts[3]);
                    cards.add(card);
                }
            }
        }

        cardPort.saveAll(cards);
    }
}
