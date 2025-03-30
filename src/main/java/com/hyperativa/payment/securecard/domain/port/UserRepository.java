package com.hyperativa.payment.securecard.domain.port;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperativa.payment.securecard.domain.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
