package com.epam.hotel.controllers;

import com.epam.hotel.domains.Request;
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
public class AdminController {

    private final RequestService requestService;

    @GetMapping({"/admin"})
    public String index(Model model, Principal principal,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        if (page == null || page < 1) { page = 1; }
        if (limit == null || limit < 1) { limit = 5; }

        Page<Request> unapprovedRequests = requestService.getPagedUnprocessedRequest(page, limit);
        System.out.println(unapprovedRequests.getTotalPages());

        int totalPages = unapprovedRequests.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("pagedList", unapprovedRequests);
        return "/admin";
    }

}
