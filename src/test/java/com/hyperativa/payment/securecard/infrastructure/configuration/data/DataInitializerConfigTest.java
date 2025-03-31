package com.hyperativa.payment.securecard.infrastructure.configuration.data;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hyperativa.payment.securecard.model.UserEntity;
import com.hyperativa.payment.securecard.port.repository.UserPort;

import java.util.Optional;

@SpringBootTest
class DataInitializerConfigTest {

    @MockBean
    private UserPort userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataInitializerConfig dataInitializerConfig;

    @Test
    void shouldCreateDefaultAdminUserWhenNotExists() {
        // Given: The "admin" user does not exist
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");

        // When: The initDatabase method is called
        dataInitializerConfig.initDatabase();  // Manually calling initDatabase()

        // Then: Verify that the userRepository.save method was called with the correct parameters
        verify(userRepository, times(1)).save(argThat(user -> 
            "admin".equals(user.getUsername()) && "encodedPassword".equals(user.getPassword())
        ));
    }

    @Test
    void shouldNotCreateAdminUserWhenExists() {
        // Given: The "admin" user already exists
        UserEntity existingUser = new UserEntity("admin", "encodedPassword");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(existingUser));

        // When: The initDatabase method is called
        dataInitializerConfig.initDatabase();  // Manually calling initDatabase()

        // Then: Verify that userRepository.save was never called since the user already exists
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }
}
