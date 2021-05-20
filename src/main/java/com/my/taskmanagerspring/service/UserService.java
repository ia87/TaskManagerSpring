package com.my.taskmanagerspring.service;

import com.my.taskmanagerspring.dto.UserDTO;
import com.my.taskmanagerspring.dto.UserRegistrationDTO;
import com.my.taskmanagerspring.dto.UsersDTO;
import com.my.taskmanagerspring.entity.Role;
import com.my.taskmanagerspring.entity.RoleType;
import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.exceptions.UserAlreadyExistException;
import com.my.taskmanagerspring.repository.UserGeneralRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserGeneralRepository userGeneralRepository;


    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UsersDTO getAllUsers() {
        return new UsersDTO(userGeneralRepository.findAll());
    }

    public Page<User> getAllUsers(Pageable pageable) {
        logger.debug("getAllUsers(Pageable pageable) method where invoked");
        return userGeneralRepository.findAll(pageable);
    }

    public Page<User> getAllUsers(Pageable pageable, String repository) {
        logger.debug("getAllUsers(Pageable pageable, String repository) method where invoked");

        return userGeneralRepository.findAll(pageable);
    }

    public User getUserById(Long id) {
        logger.debug("getUserById method invocation");
        return userGeneralRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User id = " + id));
    }

    public User getUserByEmail(String email) {
        logger.debug("getUserByEmail method invocation");
        return userGeneralRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User email = " + email));
    }


    public User saveNewUser(UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistException {
        logger.debug("saveNewUser method invocation");
        UserGeneralRepository repository = userGeneralRepository;
        if (emailExist(userRegistrationDTO.getEmail(), repository)) {
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

        return userGeneralRepository.save(user);

    }

    public boolean deleteUser(Long userId) {
        logger.debug("deleteUser method invocation");
        UserGeneralRepository repository = userGeneralRepository;
        if (repository.findById(userId).isPresent()) {
            repository.deleteById(userId);
            return true;
        }
        return false;
    }

    private boolean emailExist(String email, UserGeneralRepository repository) {
        Optional<User> byEmail = repository.findByEmail(email);
        return byEmail.isPresent();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("loadUserByUsername method invocation");

        User user = userGeneralRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username = " + username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(x -> authorities.add(new SimpleGrantedAuthority(x.getAuthority())));
        log.trace("{}", "Loaded user: " + user);

//        return new org.springframework.security.core.userdetails
// -------               .User(user.getEmail(), passwordEncoder.encode(user.getPassword()), authorities);
//                .User(user.getEmail(), user.getPassword(), authorities);
        return user;
    }


    public Optional<User> findByUserLogin(UserDTO userDTO) {
        logger.debug("findByUserLogin(UserDTO userDTO) method invocation");
        return userGeneralRepository.findByEmail(userDTO.getEmail());
    }

    public Optional<User> findByUserLogin(UserRegistrationDTO userDTO) {
        logger.debug("findByUserLogin(UserRegistrationDTO userDTO)  method invocation");
        return userGeneralRepository.findByEmail(userDTO.getEmail());
    }
}
