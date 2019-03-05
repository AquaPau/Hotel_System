package com.epam.hotel.controllers;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.User;
import com.epam.hotel.domains.enums.Status;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservationService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReservationController {

    private final UserService userService;
    private final RequestService requestService;

    @GetMapping("reservation/pay/{id}")
    public String payReservation(@PathVariable String id, Principal principal, HttpServletRequest httpRequest) {
        String referer = httpRequest.getHeader("Referer");
        User user = userService.findByLogin(principal.getName());
        Request request = requestService.findById(new Long(id));
        if (user.getRequests().contains(request)) {
            request.getReservation().setStatus(Status.PAID);
        } else {
            throw new RuntimeException(String.format("User id%s has no such request %s", user.getId(), id));
        }
        requestService.save(request);
        return "redirect:" + referer;
    }

    @GetMapping("reservation/cancel/{id}")
    public String cancelReservation(@PathVariable String id, Principal principal) {
        User user = userService.findByLogin(principal.getName());
        Request request = requestService.findById(new Long(id));
        if (user.getRequests().contains(request)) {
            request.getReservation().setStatus(Status.CANCELLED);
        } else {
            throw new RuntimeException(String.format("User id%s has no such request %s", user.getId(), id));
        }
        requestService.save(request);
        return "redirect:/index?pr_page";
    }

}
