package com.epam.hotel.controllers;

import com.epam.hotel.model.User;
import com.epam.hotel.services.SecurityService;
import com.epam.hotel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping({"/", "index"})
    public String getIndex(Model model) {
        //model.addAttribute("userList", userService.getAll());
        return "index";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String saveOrUpdate(@ModelAttribute("user") User user) {
        userService.create(user);
        securityService.autoLogin(user.getLogin(), user.getPassword());
        return "redirect:/index";
    }


}