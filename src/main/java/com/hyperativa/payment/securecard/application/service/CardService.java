package com.hyperativa.payment.securecard.application.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.infrastructure.adapter.repositories.CardAdapter;
import com.hyperativa.payment.securecard.model.CardEntity;
import com.hyperativa.payment.securecard.port.services.CardPortServices;

@Service
public class CardService implements CardPortServices{

    private final CardAdapter cardPort;

    public CardService(CardAdapter cardPort) {
        this.cardPort = cardPort;
    }

    @Override
    public void saveCard(CardRequest cardRequest) {
        CardEntity card = new CardEntity(
            cardRequest.getCardNumber(),
            cardRequest.getHolderName(),
            cardRequest.getExpirationDate(),
            cardRequest.getCvv()
        );
        cardPort.save(card);
    }

    @Override
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
