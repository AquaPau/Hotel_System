package com.epam.hotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserControllerImpl {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("helloStr", "Hello World!");
        return "index";
    }

}
