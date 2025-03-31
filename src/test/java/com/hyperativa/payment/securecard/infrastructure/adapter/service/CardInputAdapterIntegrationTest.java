package com.hyperativa.payment.securecard.infrastructure.adapter.service;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.application.exeption.InvalidCardNumberException;
import com.hyperativa.payment.securecard.model.CardEntity;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.core.io.ClassPathResource;

@SpringBootTest
class CardInputAdapterIntegrationTest {

    @Autowired
    private CardInputAdapter cardInputAdapter;


    @Test
    void shouldParseTxtFileSuccessfully() throws IOException,InterruptedException, ExecutionException {
        // Prepare a valid TXT file
        InputStream resource = new ClassPathResource("test-card.txt").getInputStream();

        // Read the content of the file to ensure it loads properly
        String content = new String(resource.readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("File content: " + content);  // For debugging

        // Create MultipartFile from InputStream
        MultipartFile multipartFile = new MockMultipartFile("file", "test-card.txt", "text/plain", resource);

        // Parse the file (this is where the actual parsing happens)
        List<CardEntity> cards = cardInputAdapter.parseTxtFile(multipartFile);

        // Assert the parsed cards
        assertNotNull(cards);


    }

    @Test
    void shouldThrowExceptionForInvalidCardRequest() {
        CardRequest invalidCardRequest = new CardRequest(
                "123456789012345", // Invalid card number
                "John Doe",
                "12/30",
                "123"
        );

        // Assert exception is thrown
        assertThrows(InvalidCardNumberException.class,
                () -> cardInputAdapter.cardRequest(invalidCardRequest),
                "Expected InvalidCardNumberException for invalid card number");
    }

    @Test
    void shouldEncryptTextSuccessfully() throws Exception {
        String plainText = "1234567890123456";

        String encryptedText = cardInputAdapter.encrypt(plainText);

        assertNotNull(encryptedText, "Encrypted text should not be null");
        assertNotEquals(plainText, encryptedText, "Encrypted text should differ from plain text");
    }
}
