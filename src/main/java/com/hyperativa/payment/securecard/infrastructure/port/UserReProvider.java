package com.hyperativa.payment.securecard.infrastructure.port;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperativa.payment.securecard.domain.model.UserEntity;

import java.util.Optional;

public interface UserReProvider extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
