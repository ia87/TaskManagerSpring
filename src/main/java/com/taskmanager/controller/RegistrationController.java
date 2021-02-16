package com.taskmanager.controller;

import com.taskmanager.dto.UserRegistrationDTO;
import com.taskmanager.exceptions.UserAlreadyExistException;
import com.taskmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute("user") @Valid UserRegistrationDTO user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())  return "register";
        try {
            userService.saveNewUser(user);
        } catch (UserAlreadyExistException e) {
            model.addAttribute("usernameError", "An account for that username/email already exists.");
            log.error("{}", e.getMessage());
            return "register";
        }
        model.addAttribute("success", "New user registered. Plz log-in.");
        return "redirect:/login";
    }
}
