package com.epam.hotel.controllers;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import com.epam.hotel.domains.Room;
import com.epam.hotel.exceptions.RoomNumberAlreadyExistsException;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoomController {

    private final RequestService requestService;
    private final RoomService roomService;

    @GetMapping("/admin/rooms/new")
    public String createRoomForm(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        model.addAttribute("headerName", "create");
        model.addAttribute("buttonName", "create");
        return "rooms";
    }

    @PostMapping("/admin/rooms/save")
    public String createRoom(@ModelAttribute("room") Room room) {
        roomService.save(room);
        return "redirect:/rooms";
    }

    @GetMapping("/admin/rooms/edit/{id}")
    public String editRoom(@PathVariable String id, Model model) {
        Room room = roomService.findById(new Long(id));
        model.addAttribute("room", room);
        model.addAttribute("headerName", "edit");
        model.addAttribute("buttonName", "save");
        return "rooms";
    }

    @GetMapping("/admin/rooms/delete/{id}")
    public String deleteRoom(@PathVariable String id) {
        roomService.deleteById(new Long(id));
        return "redirect:/rooms";
    }

    @GetMapping("/rooms")
    public String roomsTable(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "limit", required = false) Integer limit, Model model) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 7;
        }

        Page<Room> roomList = roomService.findAllRoomsPaged(page, limit);
        int totalPages = roomList.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("roomsList", roomList);

        return "roomsList";
    }

    /*@RequestMapping(value = "/admin/suitable_rooms/{id}", params = {"page"})
    public String getAllFittingRoomsPaged(@PathVariable String id,
                                          @RequestParam(value = "page", required = false) Integer page,
                                          @RequestParam(value = "limit", required = false) Integer limit, Model model) {
        if (page == null || page < 1) {
            page = 1;
        }
        if (limit == null || limit < 1) {
            limit = 7;
        }
        Request request = requestService.findById(new Long(id));
        Page<Room> roomList = roomService.findAllFittingRooms(request, page, limit);
        Reservation reservation = new Reservation();//need attention!!
        int totalPages = roomList.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("allfittingroomsList", roomList);
        model.addAttribute("requestID", id);
        model.addAttribute("reservedRoom", reservation);
        return "allfittingrooms";
    }*/

    @ExceptionHandler(RoomNumberAlreadyExistsException.class)
    public String existingRoomException(Model model) {
        model.addAttribute("errorCode", "roomexists");
        return "error";
    }


}
