package com.hyperativa.payment.securecard.application.service;

import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.hyperativa.payment.securecard.application.dto.response.CardResponse;
import com.hyperativa.payment.securecard.application.exeption.InvalidCardNumberException;
import com.hyperativa.payment.securecard.infrastructure.adapter.service.CardInputAdapter;
import com.hyperativa.payment.securecard.model.CardEntity;
import com.hyperativa.payment.securecard.port.repository.CardPort;

class CardServiceTest {

    @Mock
    private CardPort cardPort;

    @Mock
    private CardInputAdapter cardInputAdapter;

    @Mock
    private CardInputAdapter txtFileAdapter;

    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardService = new CardService(cardPort, txtFileAdapter, cardInputAdapter);
    }

    @Test
    void shouldFindCardByValidNumber() {
        String cardNumber = "4111111111111111"; 
        CardEntity cardEntity = new CardEntity(); 
        cardEntity.setId(1L);

        when(cardPort.findByCardNumber(cardNumber)).thenReturn(Optional.of(cardEntity));

        CardResponse response = cardService.findByCardNumber(cardNumber);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void shouldThrowInvalidCardNumberExceptionForInvalidCard() {
        String invalidCardNumber = "123456789012"; // Número de cartão inválido

        InvalidCardNumberException exception = assertThrows(InvalidCardNumberException.class, () -> {
            cardService.findByCardNumber(invalidCardNumber);
        });

        assertEquals("Invalid card number!", exception.getMessage());
    }

    @Test
    void shouldThrowCardNotFoundExceptionWhenCardDoesNotExist() {
        String cardNumber = "4111111111111111";

        when(cardPort.findByCardNumber(cardNumber)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cardService.findByCardNumber(cardNumber);
        });

        assertEquals("Card not found!", exception.getMessage());
    }
}
