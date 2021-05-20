package com.my.taskmanagerspring.repository;

import com.my.taskmanagerspring.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@Qualifier("JPARepo")
public class UserGeneralRepositoryJPA implements UserGeneralRepository {
    @Autowired
    private final JPAUserRepository JPAUserRepository;

    Logger logger = LoggerFactory.getLogger(UserGeneralRepositoryJPA.class);

    public UserGeneralRepositoryJPA(JPAUserRepository JPAUserRepository) {
        this.JPAUserRepository = JPAUserRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return JPAUserRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return JPAUserRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return JPAUserRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        logger.debug("findAll method invoked");
        return JPAUserRepository.findAll(pageable);
    }

    @Override
    public User save(User user) {
        return JPAUserRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        JPAUserRepository.deleteById(id);
    }
}
