package com.my.taskmanagerspring.controller;

import com.my.taskmanagerspring.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Controller
public class DefaultController {

    private final TodoService todoService;

    public DefaultController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/")
    public String home1(ModelMap model,
                        @AuthenticationPrincipal UserDetails userDetails) {
//        SecurityContextHolder.getContext().getAuthentication() != null &&
//                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
        long unfinishedTasksCount = 0;

        if (userDetails != null &&
                Objects.requireNonNull(userDetails).getUsername() != null) {
            String userName = userDetails.getUsername();
            unfinishedTasksCount = todoService.countUnfinishedTasksByEmail(userName);
        }
        model.addAttribute("datetime", LocalDateTime.now());
        model.addAttribute("unfinishedTasks", unfinishedTasksCount);
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }

}
