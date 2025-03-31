package com.hyperativa.payment.securecard.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;

class UserEntityTest {

    @InjectMocks
    private UserEntity userEntity;

    @Mock
    private UserDetails mockUserDetails;

    @BeforeEach
    void setUp() {
        // Initialize a UserEntity object before each test
        userEntity = new UserEntity("admin", "password123");
    }

    @Test
    void testUserEntityConstructor() {
        // Test constructor with parameters
        UserEntity user = new UserEntity("admin", "password123");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testGetAuthorities() {
        // Check if getAuthorities returns correct roles
        Collection<? extends GrantedAuthority> authorities = userEntity.getAuthorities();
        
        assertNotNull(authorities);
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testUserEntityAccountStatus() {
        // Check account status methods
        assertTrue(userEntity.isAccountNonExpired());
        assertTrue(userEntity.isAccountNonLocked());
        assertTrue(userEntity.isCredentialsNonExpired());
        assertTrue(userEntity.isEnabled());
    }

    @Test
    void testGetUsername() {
        // Test username getter
        assertEquals("admin", userEntity.getUsername());
    }

    @Test
    void testGetPassword() {
        // Test password getter
        assertEquals("password123", userEntity.getPassword());
    }


    @Test
    void testUserEntityEquality() {
       
        // Check if two different users are not equal
        UserEntity differentUser = new UserEntity("user", "password456");
        assertNotEquals(userEntity, differentUser);
    }
}
