package com.hyperativa.payment.securecard.application.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.application.dto.response.CardResponse;
import com.hyperativa.payment.securecard.application.exeption.CardNotFoundException;
import com.hyperativa.payment.securecard.application.exeption.InvalidCardNumberException;
import com.hyperativa.payment.securecard.application.util.CardNumberValidate;
import com.hyperativa.payment.securecard.infrastructure.adapter.data.CardAdapter;
import com.hyperativa.payment.securecard.infrastructure.adapter.service.CardInputAdapter;
import com.hyperativa.payment.securecard.model.CardEntity;
import com.hyperativa.payment.securecard.port.services.CardPortServices;

@Service
public class CardService implements CardPortServices {

    private final CardAdapter cardPort;
    private final CardInputAdapter txtFileAdapter;
    private final CardInputAdapter cardInputAdapter;

    public CardService(CardAdapter cardPort, CardInputAdapter txtFileAdapter, CardInputAdapter cardInputAdapter) {
        this.cardPort = cardPort;
        this.cardInputAdapter = cardInputAdapter;
        this.txtFileAdapter = txtFileAdapter;
    }

    @Override
    public void saveCard(CardRequest cardRequest) {

        
        CardEntity card;
        try {
            card = cardInputAdapter.cardRequest(cardRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        cardPort.save(card);
    }

    @Override
    public void processFile(MultipartFile file) {

        List<CardEntity> cards;

        try {
            cards = txtFileAdapter.parseTxtFile(file);
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        if (!cards.isEmpty()) {
            cardPort.saveAll(cards);
        }
    }

    @Override
    public CardResponse findByCardNumber(String cardNumber) {
        CardNumberValidate valiNumber = new CardNumberValidate();
        if (!valiNumber.isValidCardNumber(cardNumber)) {
            throw new InvalidCardNumberException("Invalid card number!");
        }

        return this.cardPort.findByCardNumber(cardNumber)
                .map(cardEntity -> new CardResponse(cardEntity.getId()))
                .orElseThrow(() -> new CardNotFoundException("Card not found!"));
    }
}
