package com.epam.hotel.controllers;

import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserControllerImpl implements Controller {
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userList", userService.getAll());
        return "index";
    }

}
