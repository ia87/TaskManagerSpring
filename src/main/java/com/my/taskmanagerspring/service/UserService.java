package com.my.taskmanagerspring.service;

import com.my.taskmanagerspring.dto.UserRegistrationDTO;
import com.my.taskmanagerspring.entity.Role;
import com.my.taskmanagerspring.entity.RoleType;
import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.exceptions.UserAlreadyExistException;
import com.my.taskmanagerspring.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User id = " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User email = " + email));
    }

    public User saveNewUser(UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistException {
        if (emailExist(userRegistrationDTO.getEmail())) {
            log.info("{}", "Почтовый адрес уже существует");
            throw new UserAlreadyExistException(
                "There is an account with that email address: "
                + userRegistrationDTO.getEmail());
        }
        User user = User.builder()
            .firstName(userRegistrationDTO.getFirstName())
            .lastName(userRegistrationDTO.getLastName())
            .email(userRegistrationDTO.getEmail())
            .roles(Collections.singleton(new Role(1L, RoleType.ROLE_USER.name())))
            .password(passwordEncoder.encode(userRegistrationDTO.getPassword()))
            .build();
        return userRepository.save(user);
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    private boolean emailExist(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username = " + username));
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(x -> authorities.add(new SimpleGrantedAuthority(x.getAuthority())));
        log.info("{}", "Loaded user: " + user);
        return user;
    }
}
