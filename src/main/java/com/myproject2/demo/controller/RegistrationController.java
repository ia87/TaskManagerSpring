package com.myproject2.demo.controller;

import com.myproject2.demo.dto.UserRegistrationDTO;
import com.myproject2.demo.entity.User;
import com.myproject2.demo.exceptions.UserAlreadyExistException;
import com.myproject2.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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

        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userService.saveNewUser(user);
        } catch (UserAlreadyExistException e) {
            model.addAttribute("usernameError", "An account for that username/email already exists.");
            return "register";
        }
        model.addAttribute("success", "New user registered. Plz log-in.");

        return "redirect:/login";
    }
}
