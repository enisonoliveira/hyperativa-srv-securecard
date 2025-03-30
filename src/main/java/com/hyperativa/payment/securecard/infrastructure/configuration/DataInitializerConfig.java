package com.hyperativa.payment.securecard.infrastructure.configuration;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hyperativa.payment.securecard.infrastructure.adapter.UserAdapter;
import com.hyperativa.payment.securecard.model.UserEntity;

@Configuration
public class DataInitializerConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializerConfig.class);

    private final UserAdapter userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializerConfig(UserAdapter userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initDatabase() {
        // Check if the "admin" user exists; if not, create a default one
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserEntity user = new UserEntity("admin", passwordEncoder.encode("123456"));
            userRepository.save(user);
            logger.info("Default user created: admin / 123456");
        } else {
            logger.info("Admin user already exists.");
        }
    }
}
