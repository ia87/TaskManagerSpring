package com.myproject2.demo.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.myproject2.demo.entity.Todo;
import com.myproject2.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
public class TodoService implements ITodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public Page<Todo> getTodosPageByUser(String user, Pageable pageable) {
        return todoRepository.findByUserName(user, pageable);
    }

    @Override
    public Page<Todo> getAllTodo(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    @Override
    public Optional < Todo > getTodoById(long id) {
        return todoRepository.findById(id);
    }

    @Override
    public void updateTodo(Todo todo) {
        todoRepository.save(todo);
    }

    @Override
    public void addTodo(String name, String desc, LocalDate targetDate, boolean isDone) {
        todoRepository.save(new Todo(name, desc, targetDate, isDone));
    }

    @Override
    public void deleteTodo(long id) {
        Optional < Todo > todo = todoRepository.findById(id);
        todo.ifPresent(value -> todoRepository.delete(value));
    }

    @Override
    public void saveTodo(Todo todo) {
        todoRepository.save(todo);
    }

}