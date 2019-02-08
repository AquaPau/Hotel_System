package com.epam.hotel.services;

import com.epam.hotel.model.Room;

public interface RoomService extends Service<Room> {

    public Room getByRoomNumber(int roomNumber);

}
