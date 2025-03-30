package com.hyperativa.payment.securecard.infrastructure.adapter.card;

import com.hyperativa.payment.securecard.model.CardEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class TxtFileAdapter {

    public List<CardEntity> parseTxtFile(MultipartFile file) throws IOException {
        List<CardEntity> cards = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("C")) { 
                    CardEntity card = parseCardLine(line);
                    if (card != null) {
                        cards.add(card);
                    }
                }
            }
        }
        return cards;
    }

    private CardEntity parseCardLine(String line) {
        if (line.length() < 26) { 
            return null;
        }

        String identifier = line.substring(0, 1).trim(); 
        String cardNumber = line.substring(7, 26).trim(); 

      
        String holderName = "Client " + identifier; 
        String expirationDate = "12/30"; 
        String cvv = "123"; 

        return new CardEntity(cardNumber, holderName, expirationDate, cvv);
    }
}
