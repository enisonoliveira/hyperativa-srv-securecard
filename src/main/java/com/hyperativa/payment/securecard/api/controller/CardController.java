package com.hyperativa.payment.securecard.api.controller;

import com.hyperativa.payment.securecard.api.based.CardBased;
import com.hyperativa.payment.securecard.application.dto.request.*;
import com.hyperativa.payment.securecard.port.CardServicePort;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class CardController implements CardBased {

    private final CardServicePort cardService;

    public CardController(CardServicePort cardService) {
        this.cardService = cardService;
    }

    
    public ResponseEntity<String> addCard(@RequestBody CardRequest cardRequest) {
        cardService.saveCard(cardRequest);
        return ResponseEntity.ok("Card saved successfully!");
    }

   
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty!");
        }

        try {
            cardService.processFile(file);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error processing file");
        }
    }
}
