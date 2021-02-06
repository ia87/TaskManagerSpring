package com.myproject2.demo.service;

import com.myproject2.demo.dto.UserDTO;
import com.myproject2.demo.dto.UserRegistrationDTO;
import com.myproject2.demo.dto.UsersDTO;
import com.myproject2.demo.entity.Role;
import com.myproject2.demo.entity.RoleType;
import com.myproject2.demo.entity.User;
import com.myproject2.demo.exceptions.UserAlreadyExistException;
import com.myproject2.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsersDTO getAllUsers() {
        //TODO checking for an empty user list
        return new UsersDTO(userRepository.findAll());
    }

    public Optional<User> findByUserLogin (UserDTO userDTO){
        //TODO check for user availability. password check
        return userRepository.findByEmail(userDTO.getEmail());
    }
    public Optional<User> findByUserLogin (UserRegistrationDTO userDTO){
        //TODO check for user availability. password check
        return userRepository.findByEmail(userDTO.getEmail());
    }

//    @Autowired
    @Transactional
    public User saveNewUser (UserRegistrationDTO userRegistrationDTO) throws UserAlreadyExistException {
        if (emailExist(userRegistrationDTO.getEmail())) {
            log.info("{}", "Почтовый адрес уже существует");
//            log.info("{}",userRepository.findByEmail(userRegistrationDTO.getEmail()));
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            +  userRegistrationDTO.getEmail());
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
//        System.out.println(username);
//        System.out.println(userRepository.findByEmail(username).isPresent());
        User user = userRepository.findByEmail(username).get();
        if (user == null)
            throw new UsernameNotFoundException(username);
//            log.error("{}", e.getMessage());
//            throw e;
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        user.getRoles().stream().forEach(System.out::println);
        user.getAuthorities().stream().forEach(System.out::println);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(x->authorities.add(new SimpleGrantedAuthority(x.getAuthority())));

        return new org.springframework.security.core.userdetails
                .User(user.getEmail(), passwordEncoder.encode(user.getPassword()), authorities);
//        return user;
    }
}
