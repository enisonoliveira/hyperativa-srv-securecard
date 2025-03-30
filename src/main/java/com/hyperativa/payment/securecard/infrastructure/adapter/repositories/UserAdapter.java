package com.hyperativa.payment.securecard.infrastructure.adapter.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hyperativa.payment.securecard.model.UserEntity;
import com.hyperativa.payment.securecard.port.repository.UserPort;

@Repository
public interface UserAdapter extends JpaRepository<UserEntity, Long>, UserPort {
}
