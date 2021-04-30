package com.my.taskmanagerspring.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import javax.validation.Valid;

import com.my.taskmanagerspring.entity.Todo;
import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.service.TodoService;
import com.my.taskmanagerspring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    public TodoController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/list-todos", method = RequestMethod.GET)
    public String showTodos(
            ModelMap model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @AuthenticationPrincipal UserDetails userDetails) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);
        String userName = userDetails.getUsername();

        Page<Todo> todoPage = todoService.getTodosPageByUser(userName, PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("todos", todoPage.getContent());
        model.addAttribute("todoPage", todoPage);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("datetime", LocalDateTime.now());
        return "todos/list-todos";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.GET)
    public String showAddTodoPage(ModelMap model) {
        model.addAttribute("todo", new Todo());
        return "todos/todo";
    }

    @RequestMapping(value = "/add-todo", method = RequestMethod.POST)
    public String addTodo(ModelMap model,
                          @Valid Todo todo,
                          BindingResult result,
                          @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            return "todos/todo";
        }
        if (userDetails instanceof User) todo.setUser((User) userDetails);
        System.out.println(todo.getUser());
        todoService.saveTodo(todo);
        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
    public String deleteTodo(@RequestParam long id) {
        todoService.deleteTodo(id);
        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(
            @RequestParam long id,
            ModelMap model) {
        Todo todo = todoService.getTodoById(id).orElseThrow(() -> new RuntimeException("No such TODO"));
        model.addAttribute("todo", todo);
        return "todos/update-todo";
    }

    @RequestMapping(value = "/update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model,
                             @Valid Todo todo,
                             BindingResult result,
                             @AuthenticationPrincipal UserDetails userDetails) {
        User userByEmail;

        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> log.error("{}", e));
            return "todos/todo";
        }
        try {
            userByEmail = userService.getUserByEmail(userDetails.getUsername());
            todo.setUser(userByEmail);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return "todos/update-todo";
        }
        todoService.updateTodo(todo);
        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/start-todo", method = RequestMethod.GET)
    public String startTodo(
            @RequestParam long id,
            ModelMap model) {
        Todo todo = todoService.getTodoById(id).orElseThrow(() -> new RuntimeException("No such TODO"));
        todo.setStarted(LocalDateTime.now());
        todoService.saveTodo(todo);

        return "redirect:/list-todos";
    }

    @RequestMapping(value = "/finish-todo", method = RequestMethod.GET)
    public String finishTodo(
            @RequestParam long id,
            ModelMap model) {
        Todo todo = todoService.getTodoById(id).orElseThrow(() -> new RuntimeException("No such TODO"));
        todo.setFinished(LocalDateTime.now());
        todoService.saveTodo(todo);
        return "redirect:/list-todos";
    }


}