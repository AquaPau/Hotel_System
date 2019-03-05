package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.User;
import com.epam.hotel.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    void loadUserByNotExistingUsername() {
        Optional<User> optionalUser = Optional.of(new User());
        when(userRepository.findByLogin(anyString())).thenReturn(optionalUser);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(anyString()));
    }
}