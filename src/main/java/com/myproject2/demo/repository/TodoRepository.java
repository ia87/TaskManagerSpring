package com.myproject2.demo.repository;

import java.util.List;

import com.myproject2.demo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository <Todo, Long > {
    List < Todo > findByUserName(String user);
}