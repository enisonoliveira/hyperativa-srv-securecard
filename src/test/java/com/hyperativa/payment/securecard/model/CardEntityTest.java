package com.hyperativa.payment.securecard.model;

import com.hyperativa.payment.securecard.port.repository.CardPort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.application.service.CardService;

@SpringBootTest
 class CardEntityTest {

    @Mock
    private CardPort cardRepository;  // Mocked repository interface for CardEntity.

    @Autowired
    private CardService cardService;  

    @InjectMocks
    private CardEntity cardEntity;    // Inject the mocks into the entity.

    @BeforeEach
    void setUp() {
        // Initialize a CardEntity object before each test
        cardEntity = new CardEntity("4456897999999999", "John Doe", "12/25", "123");
    }

    @Test
    void testCardEntityPersistence() {
        // Mock the repository's behavior to return the card entity when saving
        when(cardRepository.save(cardEntity)).thenReturn(cardEntity);
        
        // Simulate the behavior of the save method
        CardEntity savedCardEntity = cardRepository.save(cardEntity);

        // Verify the interactions with the mock
        verify(cardRepository, times(1)).save(cardEntity);

        // Validate the data in the saved card
        assertNotNull(savedCardEntity);
        assertEquals("4456897999999999", savedCardEntity.getCardNumber());
        assertEquals("John Doe", savedCardEntity.getHolderName());
        assertEquals("12/25", savedCardEntity.getExpirationDate());
        assertEquals("123", savedCardEntity.getCvv());
    }

    @Test
    void testCardEntityConstructor() {
        // Test constructor with parameters
        CardEntity card = new CardEntity("4456897999999999", "Jane Doe", "11/24", "321");
        assertNotNull(card);
        assertEquals("4456897999999999", card.getCardNumber());
        assertEquals("Jane Doe", card.getHolderName());
        assertEquals("11/24", card.getExpirationDate());
        assertEquals("321", card.getCvv());
    }

    @Test
    void testCardNumberLengthValidation() {
        CardRequest invalidCard = new CardRequest("12345", "Invalid User", "01/25", "000");
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cardService.saveCard(invalidCard);  // Simulating the invalid card saving behavior
        });

        assertEquals("com.hyperativa.payment.securecard.application.exeption.InvalidCardNumberException: Card number invalid : 12345", exception.getMessage());
    }

   
}
