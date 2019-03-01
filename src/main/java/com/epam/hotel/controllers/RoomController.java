package com.epam.hotel.controllers;

import com.epam.hotel.domains.*;
import com.epam.hotel.domains.enums.Status;
import com.epam.hotel.exceptions.RoomNumberAlreadyExistsException;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.ReservationService;
import com.epam.hotel.services.RoomService;
import com.epam.hotel.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.epam.hotel.utils.ControllerHelper.addUserCommonElements;
import static com.epam.hotel.utils.PaginationHelper.*;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoomController {

    private final RequestService requestService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final UserService userService;

    @GetMapping("/rooms/new")
    public String createRoomForm(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("headerName", "create");
        model.addAttribute("buttonName", "create");
        return "rooms";
    }

    @PostMapping("/rooms/save")
    public String createRoom(@ModelAttribute("room") Room room) {
        roomService.save(room);
        return "redirect:/rooms";
    }

    @GetMapping("/rooms/edit/{id}")
    public String editRoom(@PathVariable String id, Model model) {
        Room room = roomService.findById(new Long(id));
        model.addAttribute("room", room);
        model.addAttribute("headerName", "edit");
        model.addAttribute("buttonName", "save");
        return "rooms";
    }

    @GetMapping("/rooms/delete/{id}")
    public String deleteRoom(@PathVariable String id) {
        roomService.deleteById(new Long(id));
        return "redirect:/rooms";
    }

    @GetMapping("/rooms")
    public String roomsTable(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "limit", required = false) Integer limit,
                             Model model, Principal principal) {

        User user = userService.findByLogin(principal.getName());

        page = getPage(page);
        limit = getLimit(limit, 7);

        Page<Room> roomList = roomService.findAllRoomsPaged(page, limit);
        if (roomList.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", getPageNumbers(roomList));
        }

        model.addAttribute("roomsList", roomList);
        addUserCommonElements(model, user, requestService);
        return "rooms-list";
    }

    @ExceptionHandler(RoomNumberAlreadyExistsException.class)
    public String existingRoomException(Model model) {
        model.addAttribute("errorCode", "roomexists");
        return "error";
    }

    @GetMapping("/admin/suitable-rooms/{id}")
    public String getSuitableRoomsForRequest(@PathVariable String id,
                                             @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "limit", required = false) Integer limit,
                                             Model model) {
        page = getPage(page);
        limit = getLimit(limit, 7);

        Request request = requestService.findById(new Long(id));
        Reservation reservation = new Reservation();
        request.addReservation(reservation);

        Page<Room> roomList = roomService.findAllRoomsAvailableForRequest(request, page, limit);
        if (roomList.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", getPageNumbers(roomList));
        }

        model.addAttribute("requestId", request.getId());
        model.addAttribute("roomsList", roomList);
        model.addAttribute("reservation", new ReservationId());


        return "suitable-rooms";
    }

    @PostMapping("/admin/suitable-rooms/pick")
    public String getSuitableRoomsForRequest(@ModelAttribute("reservation") ReservationId reservationId) {
        Reservation reservation = new Reservation(reservationId);
        reservation.setStatus(Status.BILLSENT);
        reservationService.save(reservation);
        return "redirect:/admin";
    }

}
