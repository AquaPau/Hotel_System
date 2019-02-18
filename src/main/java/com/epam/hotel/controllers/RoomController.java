package com.epam.hotel.controllers;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.dtos.RoomDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.Room;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.services.RequestService;
import com.epam.hotel.services.RoomService;
import com.epam.hotel.services.UserService;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoomController {

    private final RequestService requestService;
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
        return "redirect:/roomseditor";
    }

    @GetMapping("rooms/edit/{id}")
    public String editRoom(@PathVariable String id, Model model) {
        Room room = roomService.getById(new Long(id));
        RoomDto roomDto = roomService.getRoomDto(room);
        model.addAttribute("room", roomDto);
        model.addAttribute("headerName", "Edit room");
        model.addAttribute("buttonName", "Save");
        return "rooms";
    }

    @GetMapping("rooms/delete/{id}")
    public String deleteRoom(@PathVariable String id) {
        roomService.delete(new Long(id));
        return "redirect:/roomseditor";
    }

    @GetMapping("rooms")
    public String roomsTable(Model model){
        List<RoomDto> roomDtoList = roomService.getRoomDtoList();
        model.addAttribute("headerName", "List of hotel rooms");
        model.addAttribute("roomsList", roomDtoList);
        return "roomsList";
    }

    @GetMapping("roomseditor")
    public String roomsEditor(Model model){
        List<RoomDto> roomDtoList = roomService.getRoomDtoList();
        model.addAttribute("headerName", "List of hotel rooms <ADMIN PAGE>");
        model.addAttribute("roomsList", roomDtoList);
        return "roomsEditor";
    }

    @GetMapping("getallrooms/{id}")
    public String getAllFittingRooms(@PathVariable String id, Model model) {
        Request request = requestService.getById(new Long(id));
        List<RoomDto> allFittingRoomsList = roomService.getAllFittingRoomsDto(request);
        ReservedRoom reservedRoom = new ReservedRoom();
        reservedRoom.setRequestID(new Long(id));
        model.addAttribute("allfittingroomsList", allFittingRoomsList);
        model.addAttribute("requestID", id);
        model.addAttribute("reservedRoom", reservedRoom);
        return "allfittingrooms";
    }

}
