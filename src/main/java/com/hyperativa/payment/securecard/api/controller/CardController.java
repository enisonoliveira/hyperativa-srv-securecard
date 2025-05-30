package com.hyperativa.payment.securecard.api.controller;

import com.hyperativa.payment.securecard.api.based.CardBased;
import com.hyperativa.payment.securecard.application.dto.request.*;
import com.hyperativa.payment.securecard.application.dto.response.CardResponse;
import com.hyperativa.payment.securecard.port.services.CardPortServices;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import com.hyperativa.payment.securecard.port.queue.FileProcessingPort;

@RestController
public class CardController implements CardBased {

    private final CardPortServices cardService;
    private final FileProcessingPort fileProcessingPort;

    public CardController(CardPortServices cardService,FileProcessingPort fileProcessingPort) {
        this.cardService = cardService;
        this.fileProcessingPort=fileProcessingPort;
    }

    
    @Override
    public ResponseEntity<Map<String, String>> addCard(CardRequest cardRequest) {
        cardService.saveCard(cardRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Card saved successfully!");
        return ResponseEntity.ok(response);  // Returning the response as JSON
    }

    @Override
    public ResponseEntity<Map<String, String>> uploadFile(MultipartFile file) {
        fileProcessingPort.queueFileProcessing(file);
        Map<String, String> response = new HashMap<>();
        response.put("message", "File uploaded successfully!");
        return ResponseEntity.ok(response);  // Returning the response as JSON
    }

    @Override
    public CardResponse searchCardByNumber(String cardByStringNumber) {
       return  cardService.findByCardNumber(cardByStringNumber);
    }

}
