package com.epam.hotel.controllers;
import com.epam.hotel.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import javax.inject.Inject;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RoomController {
    private final RoomService roomService;

}
