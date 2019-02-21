package com.epam.hotel.controllers;

import com.epam.hotel.Exceptions.LoginIsBusyException;
import com.epam.hotel.dtos.ProcessedRequestDto;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.User;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservedRoomService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserController {

    private final UserService userService;
    private final ReservedRoomService reservedRoomService;
    private final RequestService requestService;

    @RequestMapping(value = "/index", params = {"pr_page", "ur_page"})
    public String indexPaginated(@RequestParam(value = "pr_page") int prPage, @RequestParam(value = "ur_page") int urPage, Model model, Principal principal) {
        int limit = 5;
        User user = userService.getByLogin(principal.getName());
        List<Request> processedRequest = requestService.getPagedProcessedRequestByUserId(user.getId(), (prPage - 1) * limit, limit);
        List<Request> unprocessedRequest = requestService.getPagedUnprocessedRequestByUserId(user.getId(), (urPage - 1) * limit, limit);

        List<ProcessedRequestDto> processedRequestDtoList = reservedRoomService.getProcessedRequestDtoList(processedRequest);
        List<RequestDto> unprocessedRequests = requestService.getRequestDtoList(unprocessedRequest);

        model.addAttribute("unprocessedRequestPages", getPages(requestService.getUnprocessedRequestByUserIdCount(user.getId()), limit));
        model.addAttribute("processedRequestPages", getPages(requestService.getProcessedRequestByUserIdCount(user.getId()), limit));
        model.addAttribute("currentUrPage", urPage);
        model.addAttribute("currentPrPage", prPage);

        model.addAttribute("user", user);
        model.addAttribute("processedRequestList", processedRequestDtoList);
        model.addAttribute("unprocessedRequestList", unprocessedRequests);
        return "index";
    }


    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal) {
        User user = userService.getByLogin(principal.getName());
        int limit = 5;
        List<Request> processedRequest = requestService.getPagedProcessedRequestByUserId(user.getId(), 0, limit);
        List<Request> unprocessedRequest = requestService.getPagedUnprocessedRequestByUserId(user.getId(), 0, limit);

        List<ProcessedRequestDto> processedRequestDtoList = reservedRoomService.getProcessedRequestDtoList(processedRequest);
        List<RequestDto> unprocessedRequests = requestService.getRequestDtoList(unprocessedRequest);

        model.addAttribute("user", user);
        model.addAttribute("processedRequestList", processedRequestDtoList);
        model.addAttribute("unprocessedRequestList", unprocessedRequests);
        model.addAttribute("unprocessedRequestPages", getPages(requestService.getUnprocessedRequestByUserIdCount(user.getId()), limit));
        model.addAttribute("processedRequestPages", getPages(requestService.getProcessedRequestByUserIdCount(user.getId()), limit));
        model.addAttribute("currentUrPage", 1);
        model.addAttribute("currentPrPage", 1);
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
    public String loginIsBusyException(Model model) {
        model.addAttribute("errorCode", "busylogin");
        return "error";
    }

    private static List<Integer> getPages(long size, int limit) {
        int pages;
        if (size % limit != 0) {
            pages = (int) (size / limit + 1);
        } else {
            pages = (int) (size / limit);
        }
        return Stream.iterate(1, i -> i + 1).limit(pages).collect(Collectors.toList());
    }


}