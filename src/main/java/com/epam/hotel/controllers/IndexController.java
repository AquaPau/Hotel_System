package com.epam.hotel.controllers;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.User;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class IndexController {

    private final UserService userService;
    private final RequestService requestService;

    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal,
                        @RequestParam(value = "ur_page", required = false) Integer ur_page,
                        @RequestParam(value = "pr_page", required = false) Integer pr_page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        if (ur_page == null || ur_page < 1) {
            ur_page = 1;
        }
        if (pr_page == null || pr_page < 1) {
            pr_page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 3;
        }

        User user = userService.findByLogin(principal.getName());

        Page<Request> processedRequests = requestService.getPagedProcessedRequestByUser(user, pr_page, limit);
        Page<Request> unprocessedRequests = requestService.getPagedUnprocessedRequestByUser(user, ur_page, limit);

        int totalPages = unprocessedRequests.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("unprocessedPageNumbers", pageNumbers);
        }

        totalPages = processedRequests.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("processedPageNumbers", pageNumbers);
        }

        model.addAttribute("user", user);
        model.addAttribute("processedRequests", processedRequests);
        model.addAttribute("unprocessedRequests", unprocessedRequests);
        return "index";
    }



}
