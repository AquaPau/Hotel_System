package com.epam.hotel.daos;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.Room;

import java.util.List;

public interface RoomDao extends Dao<Room> {

    Room getByRoomNumber(int roomNumber);

    List<Request> getRequestsByRoomNumber(int roomNumber);

    List<ReservedRoom> getAvailableRoomsInPeriodAndCapacity(Request request);


}
