package com.hyperativa.payment.securecard.infrastructure.configuration.data;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hyperativa.payment.securecard.infrastructure.adapter.data.UserAdapter;
import com.hyperativa.payment.securecard.model.UserEntity;

import javax.annotation.PostConstruct;
import java.util.Optional;

class DataInitializerConfigTest {

    @Mock
    private UserAdapter userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializerConfig dataInitializerConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void shouldCreateDefaultAdminUserWhenNotExists() {
        // Given: The "admin" user does not exist
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");

        // When: The initDatabase method is called (which will create the user)
        dataInitializerConfig.initDatabase();

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
        dataInitializerConfig.initDatabase();

        // Then: Verify that userRepository.save was never called since the user already exists
        verify(userRepository, times(0)).save(any(UserEntity.class));
    }
}
