package com.epam.hotel.controllers;

import com.epam.hotel.domains.DeniedRequest;
import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.User;
import com.epam.hotel.services.DenyMessageService;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservationService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.epam.hotel.utils.PaginationHelper.*;
import static com.epam.hotel.utils.ControllerHelper.addAdminCommonElements;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminController {

    private final RequestService requestService;
    private final ReservationService reservationService;
    private final DenyMessageService denyMessageService;
    private final UserService userService;

    @GetMapping({"/admin"})
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        page = getPage(page);
        limit = getLimit(limit, 5);
        addPagedList(requestService.getAllPagedUnprocessedRequests(page, limit), model);
        addAdminCommonElements(model, requestService, reservationService);
        return "admin";
    }

    @GetMapping({"/admin/approved-requests"})
    public String approvedRequests(Model model,
                                   @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "limit", required = false) Integer limit) {

        page = getPage(page);
        limit = getLimit(limit, 5);
        addPagedList(reservationService.getAllApprovedReservationsPaged(page, limit), model);
        addAdminCommonElements(model, requestService, reservationService);
        return "approved-requests";
    }

    @GetMapping({"/admin/denied-requests"})
    public String deniedRequests(Model model,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @RequestParam(value = "limit", required = false) Integer limit) {

        page = getPage(page);
        limit = getLimit(limit, 5);
        addPagedList(requestService.getAllPagedDeniedRequests(page, limit), model);
        addAdminCommonElements(model, requestService, reservationService);
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
    public String users(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        page = getPage(page);
        limit = getLimit(limit, 8);
        Page<User> userList = userService.getAllUsersPaged(page, limit);
        addPagedList(userList, model);
        return "users";
    }

    @GetMapping("admin/users/delete/{id}")
    public String deleteUser(@PathVariable String id,
                             @RequestParam(value = "page", required = false) Integer page) {

        userService.deleteById(Long.valueOf(id));
        return "redirect:/admin/users?page="+page;
    }

    @GetMapping("admin/users/block/{id}")
    public String blockUser(@PathVariable String id,
                            @RequestParam(value = "page", required = false) Integer page) {
        userService.changeUserBlockForId(Long.valueOf(id));
        return "redirect:/admin/users?page="+page;
    }
}
