package com.hyperativa.payment.securecard.api.based;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;
import com.hyperativa.payment.securecard.application.dto.response.CardResponse;

@RequestMapping("/api/cards")
public interface CardBased {
    
    @PostMapping("/save")
    public ResponseEntity<String> addCard( @RequestBody CardRequest cardRequest);

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@Valid @RequestParam("file") MultipartFile file);

    @GetMapping("/search/{number}")
    public CardResponse searchCardByNumber(@Valid @PathVariable("number") String cardNumber);

}
