package com.my.taskmanagerspring.service;

import com.my.taskmanagerspring.dto.UserRegistrationDTO;
import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.exceptions.UserAlreadyExistException;
import com.my.taskmanagerspring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {
    UserService userService;
    @Mock
    private static UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void when_find_user_by_id_call_repository_findById_only_once() {
        when(userRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(User.builder().id(1L).email("test@email.com").build()));
        userService.getUserById(1L);
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    public void when_user_not_found_trow_exception() {
        Throwable thrown  = assertThrows(UsernameNotFoundException.class, () -> userService.getUserById(1L));
        assertNotNull(thrown);
    }

    @Test
    public void when_save_new_user_call_repository_save_only_once() {
        User user = User.builder().id(1L).email("test@email.com").password("pass").build();
        UserRegistrationDTO dto = UserRegistrationDTO.builder().email("test@email.com").build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any(String.class))).thenReturn("pass");

        User user1 = userService.saveNewUser(dto);
        verify(userRepository, times(1)).save(any());
        assertEquals(user, user1);
    }

    @Test
    public void when_user_exists_trow_exception() {
        String email = "test@email.com";
        UserRegistrationDTO userDTO = UserRegistrationDTO.builder().email(email).build();
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(new User()));

        Throwable thrown  = assertThrows(UserAlreadyExistException.class, () -> userService.saveNewUser(userDTO));
        assertNotNull(thrown);
    }

    @Test
    public void when_find_user_by_email_call_repository_getUserByEmail_only_once() {
        String email = "test@email.com";
        User user = User.builder().id(1L).email(email).build();
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        User userByEmail = userService.getUserByEmail(email);
        verify(userRepository, times(1)).findByEmail(any());
    }

    @Test
    public void when_user_does_not_exist_trow_exception() {
        String email = "test@email.com";
        Throwable thrown  = assertThrows(UsernameNotFoundException.class, () -> userService.getUserByEmail(email));
        assertNotNull(thrown);
    }
}