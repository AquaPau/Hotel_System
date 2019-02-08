package com.epam.hotel.daos;

import com.epam.hotel.model.Room;

public interface RoomDao extends Dao<Room> {

    public Room getByRoomNumber(int roomNumber);
}
