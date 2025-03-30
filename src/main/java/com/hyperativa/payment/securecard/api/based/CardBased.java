package com.hyperativa.payment.securecard.api.based;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hyperativa.payment.securecard.application.dto.request.CardRequest;

@RequestMapping("/api/cards")
public interface CardBased {
    
     @PostMapping
    public ResponseEntity<String> addCard(@RequestBody CardRequest cardRequest);

     @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file);
}
