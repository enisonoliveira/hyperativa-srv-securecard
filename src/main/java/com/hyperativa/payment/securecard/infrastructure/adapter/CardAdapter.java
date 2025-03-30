package com.hyperativa.payment.securecard.infrastructure.adapter;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.hyperativa.payment.securecard.model.CardEntity;
import com.hyperativa.payment.securecard.port.CardPort;

@Repository
public interface CardAdapter extends JpaRepository<CardEntity, Long>, CardPort {

}