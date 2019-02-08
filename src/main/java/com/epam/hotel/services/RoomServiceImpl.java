package com.epam.hotel.services;

import com.epam.hotel.daos.RoomDao;
import com.epam.hotel.daos.UserDao;
import com.epam.hotel.model.Room;

import java.util.List;

public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao;

    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }
    @Override
    public Room create(Room room) {
        return roomDao.create(room);
    }

    @Override
    public boolean delete(long id) {
        return roomDao.delete(id);
    }

    @Override
    public boolean update(Room room) {
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
