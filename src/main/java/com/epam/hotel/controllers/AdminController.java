package com.epam.hotel.controllers;

import com.epam.hotel.domains.DeniedRequest;
import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.User;
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

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminController {

    private final RequestService requestService;
    private final ReservationService reservationService;
    private final DenyMessageService denyMessageService;
    private final UserService userService;

    @GetMapping({"/admin"})
    public String index(Model model, Principal principal,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 5;
        }

        Page<Request> unapprovedRequests = requestService.getAllPagedUnprocessedRequest(page, limit);

        if (unapprovedRequests.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", PaginationHelper.getPageNumber(unapprovedRequests));
        }

        model.addAttribute("pagedList", unapprovedRequests);
        return "admin";
    }

    @GetMapping({"/admin/approved-requests"})
    public String approvedRequests(Model model, Principal principal,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "limit", required = false) Integer limit) {

        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 5;
        }

        Page<Reservation> approvedRequests = reservationService.getAllReservationsPaged(page, limit);
        System.out.println(approvedRequests.getTotalPages());

        if (approvedRequests.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", PaginationHelper.getPageNumber(approvedRequests));
        }

        model.addAttribute("pagedList", approvedRequests);
        return "approved-requests";
    }

    @GetMapping({"/admin/denied-requests"})
    public String deniedRequests(Model model, Principal principal,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "limit", required = false) Integer limit) {

        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 5;
        }

        Page<Request> deniedRequests = requestService.getAllPagedDeniedRequests(page, limit);

        if (deniedRequests.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", PaginationHelper.getPageNumber(deniedRequests));
        }

        model.addAttribute("pagedList", deniedRequests);
        return "denied-requests";
    }

    @PostMapping("/admin/deny")
    public String denyRequest(@ModelAttribute("denymessage") DeniedRequest deniedRequest) {
        DeniedRequest message = denyMessageService.findById(deniedRequest.getId());
        message.setReason(deniedRequest.getReason());
        denyMessageService.save(message);
        return "redirect:/admin";
    }

    @GetMapping({"/admin/users"})
    public String users(Model model, Principal principal,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 8;
        }

        Page<User> userList = userService.getAllUsersPaged(page, limit);
        System.out.println(userList.getTotalPages());

        if (userList.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", PaginationHelper.getPageNumber(userList));
        }

        model.addAttribute("pagedList", userList);
        return "users";
    }

}
