package com.epam.hotel.controllers;

import com.epam.hotel.dtos.ProcessedRequestDto;
import com.epam.hotel.Exceptions.LoginIsBusyException;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.User;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservedRoomService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;
    private final ReservedRoomService reservedRoomService;
    private final RequestService requestService;

    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal) {
        User user = userService.getByLogin(principal.getName());

        List<RequestDto> userRequestsDto = requestService.getUserRequestsDto(user.getId());
        List<RequestDto> unprocessedRequests = reservedRoomService.getAllUnprocessedRequestDtoOfUser(user, userRequestsDto);
        List<ProcessedRequestDto> processedRequestDtoList = reservedRoomService.getAllProcessedRequestDtoOfUser(user);

        model.addAttribute("user", user);
        model.addAttribute("processedRequestList", processedRequestDtoList);
        model.addAttribute("unprocessedRequestList", unprocessedRequests);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
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
        return "redirect:/login";
    }

    @ExceptionHandler(LoginIsBusyException.class)
    public String loginIsBusyException(Model model){
        model.addAttribute("errorCode", "busylogin");
        return "error";
    }

}