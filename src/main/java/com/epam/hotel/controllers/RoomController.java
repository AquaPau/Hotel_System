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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static com.epam.hotel.utils.ControllerHelper.addAdminCommonElements;
import static com.epam.hotel.utils.ControllerHelper.addUserCommonElements;
import static com.epam.hotel.utils.PaginationHelper.*;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoomController {

    private final RequestService requestService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final UserService userService;

    @GetMapping("/admin/rooms/new")
    public String createRoomForm(Model model, Principal principal) {
        int newNumber = roomService.findLastNumber() + 1;
        User user = userService.findByLogin(principal.getName());
        Room room = new Room();
        addUserCommonElements(model, user, requestService);
        addAdminCommonElements(model, requestService);
        model.addAttribute("room", room);
        model.addAttribute("number", newNumber);
        model.addAttribute("headerName", "create");
        model.addAttribute("buttonName", "create");
        return "rooms";
    }

    @PostMapping("/admin/rooms/save")
    public String createRoom(@ModelAttribute("room") Room room) {
        roomService.save(room);
        return "redirect:/admin/rooms";
    }

    @GetMapping("/admin/rooms/edit/{id}")
    public String editRoom(@PathVariable String id, Model model) {
        Room room = roomService.findById(new Long(id));
        model.addAttribute("room", room);
        model.addAttribute("headerName", "edit");
        model.addAttribute("buttonName", "save");
        addAdminCommonElements(model,requestService);
        return "rooms";
    }

    @GetMapping("/admin/rooms/delete/{id}")
    public String deleteRoom(@PathVariable String id, HttpServletRequest request) {
        roomService.deleteById(new Long(id));
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping({"index/rooms", "admin/rooms"})
    public String roomsTable(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "limit", required = false) Integer limit,
                             Model model, Principal principal,
                             HttpServletRequest httpRequest) {


        User user = userService.findByLogin(principal.getName());


        page = getPage(page);
        limit = getLimit(limit, 7);

        Page<Room> roomList = roomService.findAllRoomsPaged(page, limit);
        if (roomList.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", getPageNumbers(roomList));
        }

        if (roomList.getTotalPages() > 0) {
            String requestURI = httpRequest.getRequestURI();
            String role = requestURI.contains("admin") ? "admin" : "index";
            if (isPageBeyondTotalPages(page, roomList)) return "redirect:/" + role + "/rooms?page=" + (page - 1);
            model.addAttribute("unprocessedPageNumbers", getPageNumbers(roomList));
        }
        model.addAttribute("roomsList", roomList);
        addUserCommonElements(model, user, requestService);
        long denied = requestService.countAllDeniedRequestForAdmin();
        long processed = requestService.countAllApprovedRequestForAdmin();
        long pended = requestService.countAllPendingRequestForAdmin();
        model.addAttribute("pendedCount", pended);
        model.addAttribute("approvedCount", processed);
        return "rooms-list";
    }

    @ExceptionHandler(RoomNumberAlreadyExistsException.class)
    public String existingRoomException(Model model) {
        model.addAttribute("errorCode", "423");
        return "error";
    }

    @GetMapping("/admin/suitable-rooms")
    public String getSuitableRoomsForRequest(@RequestParam(value = "request") Long requestId,
                                             @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "limit", required = false) Integer limit,
                                             Principal principal,
                                             Model model) {
        page = getPage(page);
        limit = getLimit(limit, 5);
        User user = userService.findByLogin(principal.getName());
        Request request = requestService.findById(requestId);
        Reservation reservation = new Reservation();
        request.addReservation(reservation);

        Page<Room> roomList = roomService.findAllRoomsAvailableForRequest(request, page, limit);
        if (roomList.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", getPageNumbers(roomList));
        }
        addUserCommonElements(model, user, requestService);
        addAdminCommonElements(model, requestService);

        model.addAttribute("request", request);
        model.addAttribute("requestId", requestId);
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
