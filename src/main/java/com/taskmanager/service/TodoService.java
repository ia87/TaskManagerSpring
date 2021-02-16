package com.taskmanager.service;

import java.time.LocalDate;
import java.util.Optional;

import com.taskmanager.entity.Todo;
import com.taskmanager.entity.User;
import com.taskmanager.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class TodoService implements ITodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserService userService;


    @Override
    public Page<Todo> getTodosPageByUser(String user, Pageable pageable) {
        return todoRepository.findByUserEmail(user, pageable);
    }

    @Override
    public Page<Todo> getTodosPageByUserID(Long id, Pageable pageable) {
        return todoRepository.findByUserId(id, pageable);
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
    @Transactional
    public void addTodo(Long userId, String desc, LocalDate targetDate, boolean isDone) {
        User userById = userService.getUserById(userId);
        todoRepository.save(new Todo(desc, targetDate, userById));
    }

    @Override
    public void deleteTodo(long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public void saveTodo(Todo todo) {
        todoRepository.save(todo);
    }


    @Override
    public long countUnfinishedTasksByUserId(long id) {
        return todoRepository.countByFinishedIsNullAndUser_Id(id);
    }

    @Override
    public long countUnfinishedTasksByEmail(String email) {
        return Optional.of(todoRepository.countByFinishedIsNullAndUser_Email(email)).orElse(0L);
    }
}