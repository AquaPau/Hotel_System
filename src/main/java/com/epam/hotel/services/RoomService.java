package com.epam.hotel.services;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Room;
import org.springframework.data.domain.Page;


public interface RoomService {

    Room findById(Long id);

    void deleteById(long id);

    Room save(Room room);

    Page<Room> findAllRoomsPaged(int page, int limit);

    Page<Room> findAllRoomsAvailableForRequest(Request request, int page, int limit);

    int findLastNumber();
}
