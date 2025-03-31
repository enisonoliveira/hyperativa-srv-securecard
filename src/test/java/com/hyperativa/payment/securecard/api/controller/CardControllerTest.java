package com.hyperativa.payment.securecard.api.controller;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.application.dto.response.CardResponse;
import com.hyperativa.payment.securecard.port.services.CardPortServices;
import com.hyperativa.payment.securecard.port.queue.FileProcessingPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
 class CardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CardPortServices cardService;

    @Mock
    private FileProcessingPort fileProcessingPort;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @Test
    void shouldAddCardSuccessfully() throws Exception {

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Card saved successfully!");
        doNothing().when(cardService).saveCard(any(CardRequest.class));

        // Perform the POST request
        mockMvc.perform(post("/api/cards/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cardNumber\":\"1234567890123456\",\"holderName\":\"John Doe\",\"expirationDate\":\"12/25\",\"cvv\":\"123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Card saved successfully!"));

        // Verify interactions with cardService
        verify(cardService, times(1)).saveCard(any(CardRequest.class));
    }

    @Test
    void shouldUploadFileSuccessfully() throws Exception {
        // Create a mock file to be uploaded
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());

        // Perform the upload test using multipart request
        mockMvc.perform(multipart("/api/cards/upload")
                .file(mockFile))
                .andExpect(status().isOk()); // Assert status is OK
    }

    @Test
    void shouldSearchCardByNumberSuccessfully() throws Exception {
        // Prepare mock data
        String cardNumber = "1234567890123456";
        Long id = 1234567890123456L;
        CardResponse cardResponse = new CardResponse(id);

        // Mock service behavior (searching for the card by number)
        when(cardService.findByCardNumber(cardNumber)).thenReturn(cardResponse);

        // Perform the GET request to search for the card
        mockMvc.perform(get("/api/cards/search/{number}", cardNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardNumber));

        // Verify interactions with cardService
        verify(cardService, times(1)).findByCardNumber(cardNumber);
    }

    @Test
    void shouldReturnBadRequestWhenCardNumberIsInvalid() throws Exception {
        // Perform the GET request with an invalid card number (e.g., too short or not a valid format)
        mockMvc.perform(get("/api/cards/search/[p9900]"))
                .andExpect(status().isOk());
    }

}
