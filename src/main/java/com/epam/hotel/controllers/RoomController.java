package com.epam.hotel.controllers;
import com.epam.hotel.dtos.RoomDto;
import com.epam.hotel.model.Room;
import com.epam.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/rooms/new")
    public String createRoomForm(Model model){
        Room room = new Room();
        RoomDto roomDto = roomService.getRoomDto(room);
        model.addAttribute("room", roomDto);
        model.addAttribute("headerName", "Create new room");
        model.addAttribute("buttonName", "Create");
        return "rooms";

    }

    @PostMapping("rooms/save")
    public String createRoom(@ModelAttribute("room") RoomDto roomDto) {
        Room room = roomService.getRoomFromDto(roomDto);
        if (room.getRoomID() == 0) {
            roomService.create(room);
        } else {
            roomService.update(room);
        }
        return "redirect:/";
    }





}
