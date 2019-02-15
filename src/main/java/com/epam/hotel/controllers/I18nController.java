package com.epam.hotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class I18nController {

    @GetMapping("/language")
    public String initView() {
         return "login";
    }
}