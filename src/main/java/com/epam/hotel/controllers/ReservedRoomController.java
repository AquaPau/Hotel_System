package com.epam.hotel.controllers;

import com.epam.hotel.dtos.ProcessedRequestDto;
import com.epam.hotel.dtos.RoomDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.Room;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservedRoomService;
import com.epam.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservedRoomController {

    private final ReservedRoomService reservedRoomService;
    private final RequestService requestService;
    private final RoomService roomService;

    @PostMapping("allfittingrooms/pick/")
    public String submitRoomRequest(@ModelAttribute ReservedRoom reservedRoom) {
        reservedRoomService.create(reservedRoom);
        requestService.updatePaymentStatus(reservedRoom.getRequestID(), PaymentStatus.BILLSENT);
        return "submitform";
    }

    @GetMapping("reservation/cancel/{id}")
    public String cancelReservation(@PathVariable String id) {
        reservedRoomService.cancelReservation(new Long(id));
        return "redirect:/";
    }

    @GetMapping("reservation/view/{id}")
    public String viewReservation(@PathVariable String id, Model model) {
        ReservedRoom reservedRoom = reservedRoomService.getById(new Long(id));
        ProcessedRequestDto processedRequestDto = reservedRoomService.getProcessedRequestDto(reservedRoom);
        RoomDto room = roomService.getRoomDto(roomService.getById(reservedRoom.getRoomID()));

        model.addAttribute("request",processedRequestDto);
        model.addAttribute("room",room);

        return "viewreservation";
    }

}
