package com.hyperativa.payment.securecard.application.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hyperativa.payment.securecard.model.UserEntity;
import com.hyperativa.payment.securecard.port.repository.UserPort;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserPort userPort;

    public CustomUserDetailsService(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userPort.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
