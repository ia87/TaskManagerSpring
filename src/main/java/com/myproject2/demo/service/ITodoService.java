package com.myproject2.demo.service;

import com.myproject2.demo.entity.Todo;
import org.springframework.data.domain.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITodoService {

    Page<Todo> getTodosPageByUser(String user, Pageable pageable);

    Page<Todo> getAllTodo(Pageable pageable);

    Optional < Todo > getTodoById(long id);

    void updateTodo(Todo todo);

    void addTodo(String name, String desc, LocalDate targetDate, boolean isDone);

    void deleteTodo(long id);

    void saveTodo(Todo todo);

//    long getTodosCount(String userName);
}