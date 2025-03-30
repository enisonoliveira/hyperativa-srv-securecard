package com.hyperativa.payment.securecard.port.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperativa.payment.securecard.model.CardEntity;


public interface CardPort   extends JpaRepository<CardEntity, Long>{

    Optional<CardEntity> findByCardNumber(String cardNumber);
}
