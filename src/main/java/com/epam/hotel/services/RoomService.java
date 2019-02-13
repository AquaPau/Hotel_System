package com.epam.hotel.services;

import com.epam.hotel.dtos.RoomDto;
import com.epam.hotel.model.Room;

public interface RoomService extends Service<Room> {

    public Room getByRoomNumber(int roomNumber);

    RoomDto getRoomDto(Room room);

    Room getRoomFromDto(RoomDto roomDto);

}
