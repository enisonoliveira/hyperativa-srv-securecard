package com.hyperativa.payment.securecard.port;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperativa.payment.securecard.model.UserEntity;


public interface UserPort  extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByUsername(String username);
}
