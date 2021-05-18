package com.my.taskmanagerspring.repository;

import com.my.taskmanagerspring.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGeneralRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    User save(User user);
    void deleteById(Long id);
}
