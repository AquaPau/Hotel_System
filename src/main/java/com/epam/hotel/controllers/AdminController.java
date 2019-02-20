package com.epam.hotel.controllers;

import com.epam.hotel.dtos.ApprovedRequestDto;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminController {

    private final RequestService requestService;

    @GetMapping("/admin")
    public String adminGetAllRequests(Model model) {
        List<RequestDto> allRequestsDtoList = requestService.getAllRequestsDto();
        model.addAttribute("requestList", allRequestsDtoList);
        return "admin";
    }

    @GetMapping("/approvedrequests")
    public String adminApprovedRequestList(Model model) {
        List<ApprovedRequestDto> approvedRequestsList = requestService.getAllApprovedRequests();
        model.addAttribute("approvedRequestList", approvedRequestsList);
        return "approvedrequests";
    }

}
