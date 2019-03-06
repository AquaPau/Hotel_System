package com.epam.hotel.controllers;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.User;
import com.epam.hotel.exceptions.PasswordDoesNotMatchException;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

import static com.epam.hotel.utils.ControllerHelper.addUserCommonElements;
import static com.epam.hotel.utils.PaginationHelper.*;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class IndexController {

    private final UserService userService;
    private final RequestService requestService;

    @GetMapping({"", "/", "/index"})
    public String index(Model model, Principal principal,
                        @RequestParam(value = "ur_page", required = false) Integer ur_page,
                        @RequestParam(value = "pr_page", required = false) Integer pr_page,
                        @RequestParam(value = "dr_page", required = false) Integer dr_page,
                        @RequestParam(value = "limit", required = false) Integer limit) {

        User user = userService.findByLogin(principal.getName());
        ur_page = getPage(ur_page);
        pr_page = getPage(pr_page);
        dr_page = getPage(dr_page);
        limit = getLimit(limit, 5);

        Page<Request> unprocessedRequests = requestService.getPagedUnprocessedRequestsByUser(user, ur_page, limit);
        Page<Request> processedRequests = requestService.getPagedProcessedRequestsByUser(user, pr_page, limit);
        Page<Request> deniedRequests = requestService.getPagedDeniedRequestsByUser(user, dr_page, limit);

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

        model.addAttribute("unprocessedRequests", unprocessedRequests);
        model.addAttribute("processedRequests", processedRequests);
        model.addAttribute("deniedRequests", deniedRequests);
        addUserCommonElements(model, user, requestService);
        return "index";
    }

    @GetMapping("/index/profile")
    public String editProfile(Model model, Principal principal,
    @RequestParam(value="success",required = false) Boolean success) {
        User user = userService.findByLogin(principal.getName());
        user.setPassword("");
        model.addAttribute(user);
        model.addAttribute("currentPassword", "");
        addUserCommonElements(model, user, requestService);
        return "profile";
    }

    @PostMapping("/profile/update")
    public View updateProfile(@ModelAttribute("user") User user,
                              @ModelAttribute("currentPassword") String currentPassword) {
        userService.update(user, currentPassword);
        RedirectView redirect = new RedirectView("/index/profile?success=true");
        redirect.setExposeModelAttributes(false);
        return redirect;
    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    public String passwordDoesNotMatchException(Model model) {
        model.addAttribute("errorCode", "418");
        return "error";
    }
    @ExceptionHandler(Exception.class)
    public String randomException(Model model) {
        model.addAttribute("errorCode", "500");
        return "error";
    }

}
