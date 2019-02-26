package com.epam.hotel.controllers;

import com.epam.hotel.domains.DenyMessage;
import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import com.epam.hotel.domains.enums.Status;
import com.epam.hotel.services.DenyMessageService;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservationService;
import com.epam.hotel.services.UserService;
import com.epam.hotel.utils.PaginationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminController {

    private final RequestService requestService;
    private final ReservationService reservationService;
    private final DenyMessageService denyMessageService;

    @GetMapping({"/admin"})
    public String index(Model model, Principal principal,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        if (page == null || page < 1) { page = 1; }
        if (limit == null || limit < 1) { limit = 5; }

        Page<Request> unapprovedRequests = requestService.getPagedUnprocessedRequest(page, limit);
        System.out.println(unapprovedRequests.getTotalPages());

        if (unapprovedRequests.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", PaginationHelper.getPageNumber(unapprovedRequests));
        }

        model.addAttribute("pagedList", unapprovedRequests);
        return "/admin";
    }

    @GetMapping({"/approvedrequests"})
    public String approvedrequests(Model model, Principal principal,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        if (page == null || page < 1) { page = 1; }
        if (limit == null || limit < 1) { limit = 5; }

        Page<Reservation> approvedRequests = reservationService.getAllReservationsPaged(page, limit);
        System.out.println(approvedRequests.getTotalPages());

        if (approvedRequests.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", PaginationHelper.getPageNumber(approvedRequests));
        }

        model.addAttribute("pagedList", approvedRequests);
        return "/approvedrequests";
    }

    @GetMapping({"/deniedrequests"})
    public String deniedrequests(Model model, Principal principal,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "limit", required = false) Integer limit) {

        if (page == null || page < 1) { page = 1; }
        if (limit == null || limit < 1) { limit = 5; }

        Page<Reservation> deniedRequests = reservationService.getAllDeniedReservationsPaged(page, limit);
        System.out.println(deniedRequests.getTotalPages());

        if (deniedRequests.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", PaginationHelper.getPageNumber(deniedRequests));
        }

        model.addAttribute("pagedList", deniedRequests);
        return "/deniedrequests";
    }

    @PostMapping("admin/deny")
    public String denyRequest(@ModelAttribute("denymessage") DenyMessage denyMessage) {
        DenyMessage message = denyMessageService.findById(denyMessage.getId());
        message.setMessage(denyMessage.getMessage());
        Reservation reservation = new Reservation(message.getRequest(), 1);
        reservation.setStatus(Status.DENIED);
        message.getRequest().setReservation(reservation);
        requestService.save(message.getRequest());
        return "redirect:/admin";
    }

}
