package com.epam.hotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LanguageController {

    @GetMapping("/language")
    public String initView(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
