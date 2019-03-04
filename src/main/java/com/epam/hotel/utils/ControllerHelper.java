package com.epam.hotel.utils;

import com.epam.hotel.domains.User;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservationService;
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

    public static void addAdminCommonElements(Model model, RequestService requestService, ReservationService reservationService) {
        long denied = requestService.countAllDeniedRequestForAdmin();
        long approved = reservationService.countAllApprovedReservations();
        long unapproved = requestService.countAllPendingRequestForAdmin();

        model.addAttribute("unapprovedCount", unapproved);
        model.addAttribute("approvedCount", approved);
        model.addAttribute("deniedCount", denied);
    }

}
