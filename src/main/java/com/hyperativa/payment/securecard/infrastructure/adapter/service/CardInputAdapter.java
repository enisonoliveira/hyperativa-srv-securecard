package com.hyperativa.payment.securecard.infrastructure.adapter.service;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.application.exeption.InvalidCardNumberException;
import com.hyperativa.payment.securecard.application.util.CardNumberValidate;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.Cipher;

@Component
public class CardInputAdapter {

    private final CertificateWorkerPortServices certificateWorkerService;

    public CardInputAdapter(CertificateWorkerPortServices certificateWorkerService) {
        this.certificateWorkerService = certificateWorkerService;
    }

    public List<CardEntity> parseTxtFile(MultipartFile file) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        List<CardEntity> cards = new ArrayList<>();

        Future<?> future = executor.submit(() -> {
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        future.get();

        return cards;
    }

    public CardEntity cardRequest(CardRequest cardRequest) throws Exception {

        validExptionCard( cardRequest.getCardNumber() );

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
        validExptionCard( line.substring(7, 26).trim() );

        String identifier = line.substring(0, 1).trim();
        String cardNumber = encrypt(line.substring(7, 26).trim());


        String holderName = "Client " + identifier;
        String expirationDate = "12/30";
        String cvv = encrypt("123");

        return new CardEntity(cardNumber, holderName, expirationDate, cvv);
    }

    public String encrypt(String text) throws Exception {


        PublicKey publicKey = this.certificateWorkerService.getPublicKey();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedTextBytes = cipher.doFinal(text.getBytes());

        return Base64.getEncoder().encodeToString(encryptedTextBytes);
    }

    public void validExptionCard(String cardNumber) {
        CardNumberValidate valiNumber = new CardNumberValidate();
        if (!valiNumber.isValidCardNumber(cardNumber)) {
            throw new InvalidCardNumberException("Card number invalid : "+cardNumber);
        }
    }

}
