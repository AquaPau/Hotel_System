package com.epam.hotel.controllers;

import com.epam.hotel.domains.DeniedRequest;
import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.epam.hotel.utils.ControllerHelper.addAdminCommonElements;
import static com.epam.hotel.utils.PaginationHelper.*;


@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminController {

    private final RequestService requestService;
    private final ReservationService reservationService;
    private final DenyMessageService denyMessageService;
    private final UserService userService;

    @GetMapping({"/admin", "admin"})
    public String index(Model model,
                        @RequestParam(value = "prq_page", required = false) Integer prq_page,
                        @RequestParam(value = "arq_page", required = false) Integer arq_page,
                        @RequestParam(value = "drq_page", required = false) Integer drq_page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        prq_page = getPage(prq_page);
        arq_page = getPage(arq_page);
        drq_page = getPage(drq_page);
        limit = getLimit(limit, 5);

        Page<Request> unapprovedRequests = requestService.getAllPagedUnprocessedRequests(prq_page, limit);
        Page<Reservation> approvedRequests = reservationService.getAllApprovedReservationsPaged(arq_page, limit);
        Page<Request> deniedRequests = requestService.getAllPagedDeniedRequests(drq_page, limit);

        if (unapprovedRequests.getTotalPages() > 0) {
            if (isPageBeyondTotalPages(prq_page, unapprovedRequests))
                return "redirect:/admin?prq_page=" + (prq_page - 1);
            model.addAttribute("unapprovedPageNumbers", getPageNumbers(unapprovedRequests));
        }
        if (approvedRequests.getTotalPages() > 0) {
            if (isPageBeyondTotalPages(arq_page, approvedRequests)) return "redirect:/admin?arq_page=" + (arq_page - 1);
            model.addAttribute("approvedPageNumbers", getPageNumbers(approvedRequests));
        }
        if (deniedRequests.getTotalPages() > 0) {
            if (isPageBeyondTotalPages(drq_page, deniedRequests)) return "redirect:/admin?drq_page=" + (drq_page - 1);
            model.addAttribute("deniedPageNumbers", getPageNumbers(deniedRequests));
        }

        model.addAttribute("unapprovedRequestList", unapprovedRequests);
        model.addAttribute("approvedRequestList", approvedRequests);
        model.addAttribute("deniedRequestList", deniedRequests);
        addAdminCommonElements(model, requestService);
        return "admin";
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
        addPagedList(userService.getAllUsersPaged(page, limit), model);
        addAdminCommonElements(model, requestService);
        return "users";
    }

    @GetMapping("admin/users/delete/{id}")
    public String deleteUser(@PathVariable String id,
                             @RequestParam(value = "page", required = false) Integer page) {

        userService.deleteById(Long.valueOf(id));
        return "redirect:/admin/users?page=" + page;
    }

    @GetMapping("admin/users/block/{id}")
    public String blockUser(@PathVariable String id,
                            @RequestParam(value = "page", required = false) Integer page) {
        userService.changeUserBlockForId(Long.valueOf(id));
        return "redirect:/admin/users?page=" + page;
    }

    @GetMapping({"/admin/today-users"})
    public String usersToday(Model model,
                             @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "limit", required = false) Integer limit) {

        page = getPage(page);
        limit = getLimit(limit, 8);
        Page<Reservation> reservationList = reservationService.findAllReservationsForToday(page, limit);
        if (reservationList.getTotalPages() > 0) {
           // if (isPageBeyondTotalPages(page, reservationList)) return "redirect:/admin/today-users?page=" + (page - 1);
            model.addAttribute("pageNumbers", getPageNumbers(reservationList));
        }
        model.addAttribute("pagedList", reservationList);
        addPagedList(reservationService.findAllReservationsForToday(page, limit), model);
        addAdminCommonElements(model, requestService);
        return "today-users";
    }
}
