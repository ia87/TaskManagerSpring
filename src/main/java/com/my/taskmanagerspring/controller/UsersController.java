package com.my.taskmanagerspring.controller;

import com.my.taskmanagerspring.entity.Todo;
import com.my.taskmanagerspring.entity.User;
import com.my.taskmanagerspring.service.TodoService;
import com.my.taskmanagerspring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
public class UsersController {
    private final UserService userService;
    private final TodoService todoService;

    public UsersController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    @RequestMapping("/users")
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<User> usersPage =
                userService.getAllUsers(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("page", usersPage);
        model.addAttribute("currentPage", currentPage);
        return "users";
    }

    @RequestMapping(value = "/users/{id}/edit", method = RequestMethod.GET)
    public String showEditPage(@PathVariable(name = "id") Long id,
                               Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user";
    }


    @RequestMapping(value = "/users/{id}/todo-list", method = RequestMethod.GET)
    public String showTodosForUser(@PathVariable("id") Long userId,
                                   ModelMap model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(4);

        User userById = userService.getUserById(userId);
        Page<Todo> todoPage
                = todoService.getTodosPageByUserID(userId, PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("user", userById);
        model.addAttribute("userId", userId);
        model.addAttribute("todos", todoPage.getContent());
        model.addAttribute("todoPage", todoPage);
        model.addAttribute("currentPage", currentPage);
        return "users/list-todos";
    }

    @RequestMapping(value = "/users/{id}/add-todo", method = RequestMethod.GET)
    public String showAddTodoPage(ModelMap model,
                                  @PathVariable("id") Long userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("todo", new Todo());
        return "users/todo";
    }

    @RequestMapping(value = "/users/{id}/add-todo", method = RequestMethod.POST)
    public String addTodo(@Valid Todo todo,
                          BindingResult result,
                          @PathVariable("id") Long userId) {
        if (result.hasErrors()) {
            log.error("Errors:");
            result.getAllErrors().forEach(e -> log.error("{}", e.toString()));
            return "users/todo";
        }

        User userById = userService.getUserById(userId);
        todo.setUser(userById);
        todoService.saveTodo(todo);
        log.info("{}", "Todo saved for userId=" + userId);
        return "redirect:/users/" + userId + "/todo-list";
    }

    @RequestMapping(value = "/users/{id}/delete-todo", method = RequestMethod.GET)
    public String deleteTodo(@RequestParam long id,
                             @PathVariable("id") Long userId) {
        todoService.deleteTodo(id);
        return "redirect:/users/" + userId + "/todo-list";
    }

    @RequestMapping(value = "/users/{id}/update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam long id,
                                     ModelMap model,
                                     @PathVariable("id") Long userId) {
        Todo todo = todoService.getTodoById(id).orElseThrow(() -> new RuntimeException("No such TODO"));
        model.addAttribute("userId", userId);
        model.addAttribute("todo", todo);
        return "users/update-todo";
    }

    @RequestMapping(value = "/users/{id}/update-todo", method = RequestMethod.POST)
    public String updateTodo(@Valid Todo todo,
                             BindingResult result,
                             @AuthenticationPrincipal UserDetails userDetails) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            return "users/todo";
        }
        User userByEmail;
        try {
            userByEmail = userService.getUserByEmail(userDetails.getUsername());
            todo.setUser(userByEmail);
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            return "users/update-todo";
        }
        todoService.updateTodo(todo);
        return "redirect:/list-todos";
    }


}
