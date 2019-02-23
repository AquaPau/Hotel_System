package com.epam.hotel.configurations;

import com.epam.hotel.model.User;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class IndexController {

    private final UserService userService;

    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        model.addAttribute("user", user);

        return "indextest";
    }
}
