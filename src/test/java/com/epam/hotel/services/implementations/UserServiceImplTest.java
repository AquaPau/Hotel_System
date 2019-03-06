package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.User;
import com.epam.hotel.domains.enums.BlockStatus;
import com.epam.hotel.exceptions.PasswordDoesNotMatchException;
import com.epam.hotel.repositories.UserRepository;
import com.epam.hotel.utils.Encoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void findById() {
        Optional<User> optionalUser = Optional.of(new User());
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        User foundUser = userService.findById(anyLong());
        assertEquals(optionalUser.get(),foundUser);
    }

    @Test
    void findByNotExistingId() {
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        Assertions.assertThrows(RuntimeException.class, () -> userService.findById(anyLong()));
    }

    @Test
    void update() {
        String currentPassword = "oldpassword";
        Optional<User> optionalSavedUser = Optional.of(new User(){{setPassword(Encoder.encode(currentPassword));}});
        User parameterUser = new User(){{
            setLogin("login");
            setFirstName("name");
            setLastName("surname");
            setPassword("newpassword");
        }};

        when(userRepository.findByLogin(anyString())).thenReturn(optionalSavedUser);
        when(userRepository.save(any(User.class))).thenReturn(parameterUser);
        User updatedUser = userService.update(parameterUser, currentPassword);
        assertEquals(parameterUser,updatedUser);
    }

    @Test
    void updateWithWrongPassword() {
        String currentPassword = "oldpassword";
        String parameterPassword = "wrongpassword";
        Optional<User> optionalSavedUser = Optional.of(new User(){{setPassword(Encoder.encode(currentPassword));}});
        User parameterUser = new User(){{
            setLogin("login");
            setFirstName("name");
            setLastName("surname");
            setPassword("newpassword");
        }};

        when(userRepository.findByLogin(anyString())).thenReturn(optionalSavedUser);
        Assertions.assertThrows(PasswordDoesNotMatchException.class,() -> userService.update(parameterUser,parameterPassword));
    }

    @Test
    void updateWithNotExistingUser() {
        Optional<User> optionalSavedUser = Optional.empty();

        when(userRepository.findByLogin(anyString())).thenReturn(optionalSavedUser);
        User user = new User(){{setLogin("asd");}};
        Assertions.assertThrows(UsernameNotFoundException.class,() -> userService.update(user,anyString()));
    }

    @Test
    void deleteByNotExistingId() {
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        Assertions.assertThrows(UsernameNotFoundException.class,() -> userService.deleteById(anyLong()));
    }

    @Test
    void findByLogin() {
        Optional<User> optionalUser = Optional.of(new User());
        when(userRepository.findByLogin(anyString())).thenReturn(optionalUser);
        User foundUser = userService.findByLogin(anyString());
        assertEquals(optionalUser.get(),foundUser);
    }

    @Test
    void findByNotExistingLogin() {
        Optional<User> optionalUser = Optional.empty();
        when(userRepository.findByLogin(anyString())).thenReturn(optionalUser);
        Assertions.assertThrows(UsernameNotFoundException.class,() -> userService.findByLogin(anyString()));
    }

    @Test
    void changeUserBlockForId() {
        User user = new User(){{setBlock(BlockStatus.UNBLOCKED);}};
        when(userRepository.getOne(anyLong())).thenReturn(user);
        userService.changeUserBlockForId(anyLong());
        assertNotEquals(BlockStatus.UNBLOCKED, user.getBlock());
    }
}