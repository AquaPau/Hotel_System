package com.epam.hotel.controllers;

import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.Request;
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
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;
    private final RequestService requestService;

    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal) {
        User user = userService.getByLogin(principal.getName());
        List<RequestDto> requestDtoList = requestService.getUserRequestsDto(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("requestList", requestDtoList);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminGetAllRequests(Model model, Principal principal) {
        User user = userService.getByLogin(principal.getName());
        List<RequestDto> allRequestsDtoList = requestService.getAllRequestsDto();
        model.addAttribute("requestList", allRequestsDtoList);
        return "admin";
    }


    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String saveOrUpdate(@ModelAttribute("user") User user) {
        userService.create(user);
        return "redirect:/login";
    }


}