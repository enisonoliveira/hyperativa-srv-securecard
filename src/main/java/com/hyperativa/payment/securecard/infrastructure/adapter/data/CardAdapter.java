package com.hyperativa.payment.securecard.infrastructure.adapter.data;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.hyperativa.payment.securecard.model.CardEntity;
import com.hyperativa.payment.securecard.port.repository.CardPort;

@Repository
public interface CardAdapter extends JpaRepository<CardEntity, Long>, CardPort {

}