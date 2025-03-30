package com.hyperativa.payment.securecard.infrastructure.adapter.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hyperativa.payment.securecard.domain.model.UserEntity;
import com.hyperativa.payment.securecard.infrastructure.port.UserReProvider;

@Configuration
public class DataInitializerConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializerConfig.class);

    @Bean
    public CommandLineRunner initDatabase(UserReProvider userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                UserEntity user = new UserEntity("admin", passwordEncoder.encode("123456"));
                userRepository.save(user);
                logger.info("Usuário padrão criado: admin / 123456");
            } else {
                logger.info("Usuário admin já existe.");
            }
        };
    }
}
