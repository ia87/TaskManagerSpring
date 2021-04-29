package com.my.taskmanagerspring.service;

import com.my.taskmanagerspring.entity.Todo;
import org.springframework.data.domain.*;
import java.time.LocalDate;
import java.util.Optional;

public interface TodoService {

    Page<Todo>  getTodosPageByUser(String user, Pageable pageable);
    Page<Todo> getTodosPageByUserID(Long id, Pageable pageable);

    Page<Todo> getAllTodo(Pageable pageable);

    Optional < Todo > getTodoById(long id);

    void updateTodo(Todo todo);

    void addTodo(Long userId, String desc, LocalDate targetDate, boolean isDone);

    void deleteTodo(long id);

    void saveTodo(Todo todo);

    long countUnfinishedTasksByUserId(long id);
    long countUnfinishedTasksByEmail(String email);
//    long getTodosCount(String userName);
}