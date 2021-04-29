package com.my.taskmanagerspring.service;

import com.my.taskmanagerspring.dto.UserRegistrationDTO;
import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.exceptions.UserAlreadyExistException;
import com.my.taskmanagerspring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    public static final String TEST_EMAIL_COM = "test@email.com";
    public static final String PASSWORD = "pass";
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;


    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void shouldCallMethodFindByIdInRepositoryOneWhenCallUserServiceGetUserById() {
        when(userRepository.findById(any()))
                .thenReturn(Optional.of(User.builder().id(1L).email(TEST_EMAIL_COM).build()));
        userService.getUserById(1L);
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenUserNotFound() {
        Throwable thrown = assertThrows(UsernameNotFoundException.class, () -> userService.getUserById(1L));
        assertNotNull(thrown);
    }

    @Test
    public void shouldCallRepositoryMethodSaveOnlyOnceWhenSaveNewUser() {
        User user = User.builder().id(1L).email(TEST_EMAIL_COM).password(PASSWORD).build();
        UserRegistrationDTO dto = UserRegistrationDTO.builder().email(TEST_EMAIL_COM).build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any(String.class))).thenReturn(PASSWORD);

        User user1 = userService.saveNewUser(dto);
        verify(userRepository, times(1)).save(any());
        assertEquals(user, user1);
    }

    @Test
    public void shouldThrowExceptionWhenUserExist() {
        UserRegistrationDTO userDTO = UserRegistrationDTO.builder().email(TEST_EMAIL_COM).build();
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(new User()));

        Throwable thrown = assertThrows(UserAlreadyExistException.class, () -> userService.saveNewUser(userDTO));
        assertNotNull(thrown);
    }

    @Test
    public void shouldCallRepositoryMethodFIndByEmailOnlyOnceWhenCallUserServiceGetUserByEmail() {
        User user = User.builder().id(1L).email(TEST_EMAIL_COM).build();
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        userService.getUserByEmail(TEST_EMAIL_COM);
        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    public void shouldThrowExceptionWhenUserDoesNotExist() {
        Throwable thrown = assertThrows(UsernameNotFoundException.class, () -> userService.getUserByEmail(TEST_EMAIL_COM));
        assertNotNull(thrown);
    }
}