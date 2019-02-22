package com.epam.hotel.daos;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.Room;

import java.util.List;

public interface RoomDao extends Dao<Room> {

    Room getByRoomNumber(int roomNumber);

    List<Room> getAvailableRoomsInPeriodAndCapacity(Request request);

    List<Room> getAvailableRoomsInPeriodAndCapacity(Request request, int page, int limit);
}
