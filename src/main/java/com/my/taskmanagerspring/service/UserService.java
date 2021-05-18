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
import com.my.taskmanagerspring.repository.UserGeneralRepository;
import com.my.taskmanagerspring.util.RepositorySwitcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserGeneralRepository JPARepo;
    private final UserGeneralRepository JDBCRepo;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RequestData requestData;
    @Autowired
    private RepositorySwitcher repositorySwitcher;

    public UserService(@Qualifier("JPARepo") UserGeneralRepository JPARepo,
                       @Qualifier("JDBCRepo") UserGeneralRepository JDBCRepo,
                       PasswordEncoder passwordEncoder) {
        this.JPARepo = JPARepo;
        this.JDBCRepo = JDBCRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UsersDTO getAllUsers() {
        return new UsersDTO(JPARepo.findAll());
    }

    public Page<User> getAllUsers(Pageable pageable) {
        System.out.println("----------------------");
        System.out.println(requestData);
        System.out.println(requestData.getHeaderNames());
        System.out.println(requestData.hashCode());
        System.out.println("----------------------");
        return JPARepo.findAll(pageable);
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
            Page<User> allUsers = JDBCRepo.findAll(pageable);
            end = Instant.now().toEpochMilli();
            log.info("Request took: " + (end - start));
            return allUsers;
        } else {
            log.info("Getting data from JPA repo");
            start = Instant.now().toEpochMilli();
            Page<User> all = JPARepo.findAll(pageable);
            end = Instant.now().toEpochMilli();
            log.info("Request took: " + (end - start));
            return all;
        }
    }

    public User getUserById(Long id) {
        return JPARepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User id = " + id));
    }

    public User getUserByEmail(String email) {
        return JPARepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User email = " + email));
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

        return JPARepo.save(user);

    }

    public boolean deleteUser(Long userId) {
        if (JPARepo.findById(userId).isPresent()) {
            JPARepo.deleteById(userId);
            return true;
        }
        return false;
    }

    private boolean emailExist(String email) {
        Optional<User> byEmail = JPARepo.findByEmail(email);
        return byEmail.isPresent();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = JPARepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username = " + username));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(x -> authorities.add(new SimpleGrantedAuthority(x.getAuthority())));
        log.info("{}", "Loaded user: " + user);

//        return new org.springframework.security.core.userdetails
// -------               .User(user.getEmail(), passwordEncoder.encode(user.getPassword()), authorities);
//                .User(user.getEmail(), user.getPassword(), authorities);
        return user;
    }


    public Optional<User> findByUserLogin(UserDTO userDTO) {
        return JPARepo.findByEmail(userDTO.getEmail());
    }

    public Optional<User> findByUserLogin(UserRegistrationDTO userDTO) {
        return JPARepo.findByEmail(userDTO.getEmail());
    }
}
