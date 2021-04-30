package com.my.taskmanagerspring.repository;

import java.util.Optional;

import com.my.taskmanagerspring.entity.Todo;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    long countByFinishedIsNull();

    Optional<Todo> findById(Long id);

    long countByFinishedIsNullAndUser_Id(Long id);

    long countByFinishedIsNullAndUser_Email(String email);

    Page<Todo> findAll(Pageable pageable);

    Page<Todo> findByUserId(Long id, Pageable pageable);

    Page<Todo> findByUserEmail(String email, Pageable pageable);
}