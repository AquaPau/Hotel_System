package com.epam.hotel.controllers;

import com.epam.hotel.dtos.ApprovedRequestDto;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminController {

    private final RequestService requestService;

    @GetMapping("/admin?page={pageNumber}")
    public String adminGetRequestsByPage(Model model) {
        return "admin";
    }

    @GetMapping("/admin")
    public String adminGetAllRequests(Model model) {
        List<RequestDto> allRequestsDtoList = requestService.getAllRequestsDto();
        Integer numberOfPages = allRequestsDtoList.size() / 5 + 1;
        ArrayList<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= numberOfPages; i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("requestList", allRequestsDtoList);
        model.addAttribute("pageNumbers", pageNumbers);
        return "admin";
    }

    @GetMapping("/approvedrequests")
    public String adminApprovedRequestList(Model model) {
        List<ApprovedRequestDto> approvedRequestsList = requestService.getAllApprovedRequests();
        model.addAttribute("approvedRequestList", approvedRequestsList);
        return "approvedrequests";
    }

}
