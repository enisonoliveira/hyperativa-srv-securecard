package com.hyperativa.payment.securecard.infrastructure.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hyperativa.payment.securecard.infrastructure.port.UserReProvider;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserReProvider userRepository;

    public CustomUserDetailsServiceImpl(UserReProvider userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
