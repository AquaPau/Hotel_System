package com.epam.hotel.utils;

import com.epam.hotel.domains.User;
import com.epam.hotel.services.RequestService;
import org.springframework.ui.Model;

public class ControllerHelper {
    private ControllerHelper() {
    }


    public static void addUserCommonElements(Model model, User user, RequestService requestService) {
        long denied = requestService.countDeniedRequestByUser(user);
        long processed = requestService.countProcessedRequestByUser(user);
        long unprocessed = requestService.countUnprocessedRequestByUser(user);

        model.addAttribute("user", user);
        model.addAttribute("deniedCount", denied);
        model.addAttribute("unprocessedCount", unprocessed);
        model.addAttribute("processedCount", processed);
    }

}
