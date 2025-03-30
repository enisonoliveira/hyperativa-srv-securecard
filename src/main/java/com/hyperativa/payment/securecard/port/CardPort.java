package com.hyperativa.payment.securecard.port;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperativa.payment.securecard.model.CardEntity;


public interface CardPort   extends JpaRepository<CardEntity, Long>{

    void saveAll(List<CardEntity> cards);  // Novo método
    Optional<CardEntity> findByCardNumber(String cardNumber);
}
