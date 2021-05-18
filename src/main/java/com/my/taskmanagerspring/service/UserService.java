package com.my.taskmanagerspring.service;

import com.my.taskmanagerspring.dao.UserDAO;
import com.my.taskmanagerspring.dto.UserDTO;
import com.my.taskmanagerspring.dto.UserRegistrationDTO;
import com.my.taskmanagerspring.dto.UsersDTO;
import com.my.taskmanagerspring.entity.Role;
import com.my.taskmanagerspring.entity.RoleType;
import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.exceptions.UserAlreadyExistException;
import com.my.taskmanagerspring.interceptor.RequestData;
import com.my.taskmanagerspring.repository.UserRepository;
import com.my.taskmanagerspring.util.RepositorySwitcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;
    @Autowired
    private RequestData requestData;
    @Autowired
    private RepositorySwitcher repositorySwitcher;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDAO userDAO) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }

    public UsersDTO getAllUsers() {
        return new UsersDTO(userRepository.findAll());
    }

    public Page<User> getAllUsers(Pageable pageable) {
        System.out.println("----------------------");
        System.out.println(requestData);
        System.out.println(requestData.getHeaderNames());
        System.out.println(requestData.hashCode());
        System.out.println("----------------------");
        userDAO.getAllUsers(pageable);
        return userRepository.findAll(pageable);
    }

    public Page<User> getAllUsers(Pageable pageable, String repository) {
        System.out.println("----------------------");
        System.out.println(requestData);
        System.out.println(requestData.getHeaderNames());
        System.out.println(requestData.hashCode());
        System.out.println(repositorySwitcher.getRepository());
        System.out.println("----------------------");

        Long start, end;
        if (repository.equals("jdbc")) {
            log.info("Getting data from JDBC repo");
            start = Instant.now().toEpochMilli();
            Page<User> allUsers = userDAO.getAllUsers(pageable);
            end = Instant.now().toEpochMilli();
            log.info("Request took: " + (end - start));
            return allUsers;
        } else {
            log.info("Getting data from JPA repo");
            start = Instant.now().toEpochMilli();
            Page<User> all = userRepository.findAll(pageable);
            end = Instant.now().toEpochMilli();
            log.info("Request took: " + (end - start));
            return all;
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User id = " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User email = " + email));
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
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username = " + username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(x -> authorities.add(new SimpleGrantedAuthority(x.getAuthority())));
        log.info("{}", "Loaded user: " + user);

//        return new org.springframework.security.core.userdetails
// -------               .User(user.getEmail(), passwordEncoder.encode(user.getPassword()), authorities);
//                .User(user.getEmail(), user.getPassword(), authorities);
        return user;
    }


    public Optional<User> findByUserLogin(UserDTO userDTO) {
        return userRepository.findByEmail(userDTO.getEmail());
    }

    public Optional<User> findByUserLogin(UserRegistrationDTO userDTO) {
        return userRepository.findByEmail(userDTO.getEmail());
    }
}
