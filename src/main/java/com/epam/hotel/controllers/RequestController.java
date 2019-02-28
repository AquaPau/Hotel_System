package com.epam.hotel.controllers;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.User;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.UserService;
import com.epam.hotel.utils.BookingHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RequestController {

    private final UserService userService;
    private final RequestService requestService;

    @GetMapping("/request/new")
    public String createRequestForm(Model model) {
        Request request = new Request();

        request.setCheckIn(BookingHelper.getTodayPlusDays(1));
        request.setCheckOut(BookingHelper.getTodayPlusDays(8));

        model.addAttribute("request", request);
        model.addAttribute("header", "create");
        model.addAttribute("buttonName", "create");
        return "request";
    }

    @PostMapping("request/save")
    public String createRequest(@ModelAttribute("request") Request request, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        if (request.getDeniedRequest() == null) {
            request.createDeniedRequest();
        }
        user.addRequest(request);
        userService.save(user);
        return "redirect:/index";
    }

    @GetMapping("request/edit/{id}")
    public String editRequest(@PathVariable String id, Model model) {
        Request request = requestService.findById(new Long(id));
        model.addAttribute("request", request);
        model.addAttribute("header", "edit");
        model.addAttribute("buttonName", "save");
        return "request";
    }

    @GetMapping("request/delete/{id}")
    public String deleteRequest(@PathVariable String id, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Request request = requestService.findById(new Long(id));
        user.removeRequest(request);
        userService.save(user);
        return "redirect:/index";
    }

    @GetMapping("request/view/{id}")
    public String viewRequest(@PathVariable String id, Model model) {
        Request request = requestService.findById(new Long(id));
        model.addAttribute("request", request);
        return "view-request";
    }

}