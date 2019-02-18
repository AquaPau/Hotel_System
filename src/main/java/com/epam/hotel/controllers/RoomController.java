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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/rooms/new")
    public String createRoomForm(Model model) {
        Room room = new Room();
        RoomDto roomDto = roomService.getRoomDto(room);
        model.addAttribute("room", roomDto);
        model.addAttribute("headerName", "create");
        model.addAttribute("buttonName", "create");
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
        return "redirect:/roomseditor";
    }

    @GetMapping("rooms/edit/{id}")
    public String editRoom(@PathVariable String id, Model model) {
        Room room = roomService.getById(new Long(id));
        RoomDto roomDto = roomService.getRoomDto(room);
        model.addAttribute("room", roomDto);
        model.addAttribute("headerName", "edit");
        model.addAttribute("buttonName", "save");
        return "rooms";
    }

    @GetMapping("rooms/delete/{id}")
    public String deleteRoom(@PathVariable String id) {
        roomService.delete(new Long(id));
        return "redirect:/roomseditor";
    }

    @GetMapping("rooms")
    public String roomsTable(Model model) {
        List<RoomDto> roomDtoList = roomService.getRoomDtoList();
        model.addAttribute("roomsList", roomDtoList);
        return "roomsList";
    }

    @GetMapping("roomseditor")
    public String roomsEditor(Model model) {
        List<RoomDto> roomDtoList = roomService.getRoomDtoList();
        model.addAttribute("headerName", "List of hotel rooms <ADMIN PAGE>");
        model.addAttribute("roomsList", roomDtoList);
        return "roomsEditor";
    }


}
