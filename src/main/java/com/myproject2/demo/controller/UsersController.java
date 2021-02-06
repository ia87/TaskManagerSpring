package com.myproject2.demo.controller;

import com.myproject2.demo.dto.UserDTO;
import com.myproject2.demo.dto.UserRegistrationDTO;
import com.myproject2.demo.dto.UsersDTO;
import com.myproject2.demo.entity.User;
import com.myproject2.demo.exceptions.UserAlreadyExistException;
import com.myproject2.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
//@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String index(Model model){
        UsersDTO allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers.getUsers());
        model.addAttribute("name11", "SomeName");
        log.info("{}", allUsers);
        return "/users";
    }

//    @GetMapping("/registration")
//    public String showRegistrationForm(WebRequest request, Model model) {
//        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
//        model.addAttribute("user", userRegistrationDTO);
//        return "users/registration";
//    }
//
//    @PostMapping("/registration")
//        public ModelAndView registerUserAccount(
//            @ModelAttribute("user") @Valid UserRegistrationDTO userRegistrationDTO,
//            HttpServletRequest request, Errors errors) {
//            ModelAndView mav = new ModelAndView("users/registration");
//        try {
//                userService.saveNewUser(userRegistrationDTO);
//        } catch (UserAlreadyExistException uaeEx) {
//            mav.addObject("message", "An account for that username/email already exists.");
//            return mav;
//        }
//
//        return new ModelAndView("users/successRegister", "user", userRegistrationDTO);
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm(WebRequest request, Model model) {
//        UserDTO userDto = new UserDTO();
//        model.addAttribute("user", userDto);
//        return "users/login";
//    }
//
//    @PostMapping("/login")
//    public ModelAndView loginUserAccount(
//            @ModelAttribute("user") @Valid UserDTO userDto,
//            HttpServletRequest request, Errors errors) {
//            ModelAndView mav = new ModelAndView("users/login");
//            Optional<User> byUserLogin = userService.findByUserLogin(userDto);
//            mav.addObject("message", "An account for that username/email already exists.");
////            return mav;
//
//            return new ModelAndView("users/index2", "user", userDto);
//    }

}
