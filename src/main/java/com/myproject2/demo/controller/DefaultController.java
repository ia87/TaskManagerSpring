package com.myproject2.demo.controller;

import com.myproject2.demo.entity.PersonForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class DefaultController {

    @GetMapping("/")
    public String home1() {
        return "/home";
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String user() {
        return "/user";
    }

    @GetMapping("/about")
    public String about() {
        return "/about";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/register")
    public String reg() {
        return "/register";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }


    @GetMapping("/form")
    public ModelAndView  showForm() {
        PersonForm personForm = new PersonForm();
        return new ModelAndView("form", "personForm", personForm);
    }

    @PostMapping("/form")
    public String checkPersonInfo(@Valid PersonForm personForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "form";
        }

        return "redirect:/results";
    }

    @GetMapping("/results")
    public String  showResults() {
        return "results";
    }


}
