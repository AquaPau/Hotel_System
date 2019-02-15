package com.epam.hotel.daos;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.Room;

import java.util.List;
import java.util.Set;

public interface RoomDao extends Dao<Room> {

    public Room getByRoomNumber(int roomNumber);

    public void addToReservedRooms(Request request, Room room);

    public Set<Integer> getAllReservedRooms();

    public List<Request> getRequestsByRoomNumber(int roomNumber);

}
