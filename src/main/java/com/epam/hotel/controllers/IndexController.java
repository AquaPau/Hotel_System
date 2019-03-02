package com.epam.hotel.controllers;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Room;
import com.epam.hotel.domains.User;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.RoomService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static com.epam.hotel.utils.ControllerHelper.addUserCommonElements;
import static com.epam.hotel.utils.PaginationHelper.*;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class IndexController {

    private final UserService userService;
    private final RequestService requestService;
    private final RoomService roomService;

    @GetMapping({"/", "/index"})
    public String index(Model model, Principal principal,
                        @RequestParam(value = "ur_page", required = false) Integer ur_page,
                        @RequestParam(value = "pr_page", required = false) Integer pr_page,
                        @RequestParam(value = "dr_page", required = false) Integer dr_page,
                        @RequestParam(value = "rl_page", required = false) Integer rl_page,
                        @RequestParam(value = "limit", required = false) Integer limit,
                        @RequestParam(value = "rl_size", required = false) Integer rl_size) {

        User user = userService.findByLogin(principal.getName());
        ur_page = getPage(ur_page);
        pr_page = getPage(pr_page);
        dr_page = getPage(dr_page);
        rl_page = getPage(rl_page);
        limit = getLimit(limit, 5);
        rl_size = getLimit(rl_size, 7);

        Page<Request> unprocessedRequests = requestService.getPagedUnprocessedRequestsByUser(user, ur_page, limit);
        Page<Request> processedRequests = requestService.getPagedProcessedRequestsByUser(user, pr_page, limit);
        Page<Request> deniedRequests = requestService.getPagedDeniedRequestsByUser(user, dr_page, limit);
        Page<Room> roomList = roomService.findAllRoomsPaged(rl_page, rl_size);

        if (unprocessedRequests.getTotalPages() > 0) {
            if (isPageBeyondTotalPages(ur_page, unprocessedRequests)) return "redirect:index?ur_page=" + (ur_page - 1);
            model.addAttribute("unprocessedPageNumbers", getPageNumbers(unprocessedRequests));
        }
        if (processedRequests.getTotalPages() > 0) {
            if (isPageBeyondTotalPages(pr_page, processedRequests)) return "redirect:index?pr_page=" + (pr_page - 1);
            model.addAttribute("processedPageNumbers", getPageNumbers(processedRequests));
        }
        if (deniedRequests.getTotalPages() > 0) {
            model.addAttribute("deniedPageNumbers", getPageNumbers(deniedRequests));
            if (isPageBeyondTotalPages(dr_page, deniedRequests)) return "redirect:index?dr_page=" + (dr_page - 1);
        }
        if (roomList.getTotalPages() > 0) {
            if (isPageBeyondTotalPages(rl_page, roomList)) return "redirect:admin?rl_page=" + (rl_page - 1);
            model.addAttribute("roomListNumbers", getPageNumbers(roomList));
        }

        model.addAttribute("unprocessedRequests", unprocessedRequests);
        model.addAttribute("processedRequests", processedRequests);
        model.addAttribute("deniedRequests", deniedRequests);
        model.addAttribute("roomsList", roomList);
        addUserCommonElements(model, user, requestService);
        return "index";
    }


}
