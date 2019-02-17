package com.epam.hotel.controllers;

import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservedRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservedRoomController {

    private final ReservedRoomService reservedRoomService;
    private final RequestService requestService;

    @PostMapping("allfittingrooms/pick/")
    public String submitRoomRequest(@ModelAttribute ReservedRoom reservedRoom) {
        reservedRoomService.create(reservedRoom);
        requestService.updatePaymentStatus(reservedRoom.getRequestID(), PaymentStatus.BILLSENT);
        return "submitform";
    }

}
