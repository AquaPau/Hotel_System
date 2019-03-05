package com.epam.hotel.controllers;

import com.epam.hotel.domains.User;
import com.epam.hotel.domains.enums.Permission;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import java.security.Principal;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

import static com.epam.hotel.utils.ControllerHelper.*;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HttpErrorController implements ErrorController {
    private final RequestService requestService;
    private final UserService userService;

    @GetMapping("/error")
    public String handleError(Model model, Principal principal,
                              HttpServletRequest httpRequest) {
        int errorCode = getErrorCode(httpRequest);


        User user = userService.findByLogin(principal.getName());
        model.addAttribute("errorCode", errorCode);
        if (user.getPermission() == Permission.USER) {
            addUserCommonElements(model, user, requestService);
        } else if (user.getPermission() == Permission.ADMIN) {
            addAdminCommonElements(model, requestService);
        }


        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute(ERROR_STATUS_CODE);
    }

}