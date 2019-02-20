package com.epam.hotel.controllers;

import com.epam.hotel.dtos.ApprovedRequestDto;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminController {

    private final RequestService requestService;

    @RequestMapping(value = "/admin", params = {"page"})
    public String adminGetRequestsByPage(@RequestParam(value = "page") int page, Model model) {
        List<RequestDto> pagedList = requestService.getRequestsByPage(Integer.valueOf(page), 5);
        model.addAttribute("pagedList", pagedList);
        return "/admin";
    }

    @RequestMapping(value = "/admin", params = {"page"})
    public String adminGetAllRequests(@RequestParam(value = "page") int page, Model model) {
        List<RequestDto> allRequestsDtoList = requestService.getAllRequestsDto();
        Integer numberOfPages = allRequestsDtoList.size() / 5 + 1;
        ArrayList<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= numberOfPages; i++) {
            pageNumbers.add(i);
        }
        List<RequestDto> pagedList = requestService.getRequestsByPage(1, 5);
        model.addAttribute("requestList", allRequestsDtoList);
        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("pagedList", pagedList);
        return "/admin";
    }

    @GetMapping("/approvedrequests")
    public String adminApprovedRequestList(Model model) {
        List<ApprovedRequestDto> approvedRequestsList = requestService.getAllApprovedRequests();
        model.addAttribute("approvedRequestList", approvedRequestsList);
        return "approvedrequests";
    }

}
