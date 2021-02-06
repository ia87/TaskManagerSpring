package com.myproject2.demo.service;

import com.myproject2.demo.entity.Todo;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITodoService {

    List <Todo> getTodosByUser(String user);

    Optional < Todo > getTodoById(long id);

    void updateTodo(Todo todo);

    void addTodo(String name, String desc, LocalDate targetDate, boolean isDone);

    void deleteTodo(long id);

    void saveTodo(Todo todo);
}