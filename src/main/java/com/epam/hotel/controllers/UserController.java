package com.epam.hotel.controllers;

import com.epam.hotel.model.User;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @GetMapping({"/", "index"})
    public String getIndex(Model model) {
        //model.addAttribute("userList", userService.getAll());
        return "index";
    }

    @GetMapping("/login")
    public String getUsers(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String saveOrUpdate(@ModelAttribute User user) {
        userService.create(user);
        return "redirect:index";
    }


}