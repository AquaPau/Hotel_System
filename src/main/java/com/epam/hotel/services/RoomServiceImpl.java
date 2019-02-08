package com.epam.hotel.services;

import com.epam.hotel.daos.RoomDao;
import com.epam.hotel.daos.UserDao;
import com.epam.hotel.enums.Capacity;
import com.epam.hotel.enums.ClassID;
import com.epam.hotel.model.Room;

import java.util.List;

public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao;

    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public void roomValidation(Room room) {

        if (room.getPrice() == null || room.getRoomNumber() == 0) {
            throw new IllegalArgumentException("Room is not valid: fill the required fields");
        } else if (room.getCapacity() == null) {
            room.setCapacity(Capacity.SINGLE);
        } else if (room.getClassID() == null) {
            room.setClassID(ClassID.STANDARD);
        }
    }

    @Override
    public Room create(Room room) {
        roomValidation(room);
        return roomDao.create(room);
    }

    @Override
    public boolean delete(long id) {
        return roomDao.delete(id);
    }

    @Override
    public boolean update(Room room) {
        roomValidation(room);
        return roomDao.update(room);
    }

    @Override
    public List<Room> getAll() {
        return roomDao.getAll();
    }

    @Override
    public Room getById(long id) {
        return roomDao.getById(id);
    }

    @Override
    public Room getByRoomNumber(int roomNumber) {
        return roomDao.getByRoomNumber(roomNumber);
    }
}
