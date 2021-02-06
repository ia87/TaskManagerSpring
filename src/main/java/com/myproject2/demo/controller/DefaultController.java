package com.myproject2.demo.controller;

import com.myproject2.demo.dto.UserRegistrationDTO;
import com.myproject2.demo.entity.PersonForm;
import com.myproject2.demo.entity.User;
import com.myproject2.demo.exceptions.UserAlreadyExistException;
import com.myproject2.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class DefaultController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home1() {
        return "/home";
    }

    @GetMapping("/home")
    public String home() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            System.out.println("Logged user role:");
            ((UserDetails) principal).getAuthorities().forEach(System.out::println);
        } else {
            String username = principal.toString();
        }
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
    public ModelAndView reg() {
        return  new ModelAndView("register", "user", new UserRegistrationDTO());
//        return "/register";
    }

    @PostMapping("/register")
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserRegistrationDTO user,
            HttpServletRequest request, Errors errors, ModelMap model
    ) {
        try {
            User registered = userService.saveNewUser(user);
        } catch (UserAlreadyExistException uaeEx) {
            model.addAttribute("message", "An account for that username/email already exists.");
            return new ModelAndView("register", model);
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("message", "New user created. Plz log in.");
//        return new ModelAndView("login","message", "New user created. Plz log in.");
        return modelAndView;
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

    @GetMapping("/test")
    public String  showTestPage() {
        return "test";
    }

//    @GetMapping("/users")
//    public String  showAllUsers() {
//        return "users";
//    }


}
