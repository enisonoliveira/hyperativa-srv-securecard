package com.hyperativa.payment.securecard.infrastructure.adapter.service;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.model.CardEntity;
import com.hyperativa.payment.securecard.port.services.CertificateWorkerPortServices;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;

@Component
public class CardInputAdapter {

    private final CertificateWorkerPortServices certificateWorkerService;


    public CardInputAdapter(CertificateWorkerPortServices certificateWorkerService){
        this.certificateWorkerService=certificateWorkerService;
    }


    public List<CardEntity> parseTxtFile(MultipartFile file) throws Exception {
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
    
    public CardEntity cardRequest(CardRequest cardRequest) throws Exception { 
        return new CardEntity(
            encrypt(cardRequest.getCardNumber()), 
            cardRequest.getHolderName(),
            cardRequest.getExpirationDate(),
            encrypt(cardRequest.getCvv()) 
        );
    }

    private CardEntity parseCardLine(String line) throws Exception {

        
        if (line.length() < 26) { 
            return null;
        }

        String identifier = line.substring(0, 1).trim(); 
        String cardNumber = encrypt (line.substring(7, 26).trim()); 

      
        String holderName = "Client " + identifier; 
        String expirationDate = encrypt("12/30"); 
        String cvv = encrypt("123"); 

        return new CardEntity(cardNumber, holderName, expirationDate, cvv);
    }

    private String encrypt(String text) throws Exception {
        // Get the public key (to encrypt, you would typically use the public key)
        PublicKey publicKey = this.certificateWorkerService.getPublicKey();

        // Initialize the cipher for RSA encryption
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        // Encrypt the text
        byte[] encryptedTextBytes = cipher.doFinal(text.getBytes());

        // Convert the encrypted bytes to a Base64 encoded string (to make it readable)
        return Base64.getEncoder().encodeToString(encryptedTextBytes);
    }

}
