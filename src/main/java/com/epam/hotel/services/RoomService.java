package com.epam.hotel.services;

import com.epam.hotel.dtos.RoomDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.Room;

import java.util.List;

public interface RoomService extends Service<Room> {

    Room getByRoomNumber(int roomNumber);

    List<RoomDto> getAllRoomsDto();

    List<RoomDto> getAllFittingRoomsDto(Request request);

/*    public void addToReservedRooms(Long requestID, int roomNumber);*/

    RoomDto getRoomDto(Room room);

    Room getRoomFromDto(RoomDto roomDto);

    List<RoomDto> getRoomDtoList();

}
