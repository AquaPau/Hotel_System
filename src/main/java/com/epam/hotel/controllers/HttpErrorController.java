package com.epam.hotel.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Controller
public class HttpErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(Model model, HttpServletRequest httpRequest) {
        int errorCode = getErrorCode(httpRequest);
        model.addAttribute("errorCode", errorCode);
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