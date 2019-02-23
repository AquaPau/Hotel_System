package com.epam.hotel.controllers;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.User;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RequestController {

    private final UserService userService;
    private final RequestService requestService;

    @GetMapping("/request/new")
    public String createRequestForm(Model model, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Request request = new Request();
        model.addAttribute("request", request);
        model.addAttribute("header", "create");
        model.addAttribute("buttonName", "create");
        return "request";
    }

    @PostMapping("request/save")
    public String createRequest(@ModelAttribute("request") Request request) {
        requestService.save(request);
        return "redirect:/index";
    }

    /*
    @GetMapping("request/edit/{id}")
    public String editRequest(@PathVariable String id, Model model) {
        Request request = requestService.getById(new Long(id));
        RequestDto requestDto = requestService.getRequestDto(request);
        model.addAttribute("request", requestDto);
        model.addAttribute("header", "edit");
        model.addAttribute("buttonName", "save");
        return "request";
    }

    @GetMapping("request/delete/{id}")
    public String deleteRequest(@PathVariable String id) {
        requestService.delete(new Long(id));
        return "redirect:/";
    }

    @GetMapping("request/pay/{id}")
    public String payRequest(@PathVariable String id, HttpServletRequest request) {
        requestService.updatePaymentStatus(new Long(id), PaymentStatus.PAID);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }*/


}