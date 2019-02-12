package com.epam.hotel.controllers;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.converters.RequestConverter;
import com.epam.hotel.model.User;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class RequestController {

    private final UserService userService;
    private final RequestService requestService;

    @GetMapping("/request/new")
    public String createRequestForm(Model model, Principal principal) {
        User user = userService.getByLogin(principal.getName());
        Request request = new Request();
        request.setUserID(user.getId());
        model.addAttribute("request", RequestConverter.getRequestConverter(request));
        return "request";
    }

    @PostMapping("request/new")
    public String createRequest(@ModelAttribute("request") RequestConverter request) {
        requestService.create(request.getRequest());
        return "redirect:/";
    }
}