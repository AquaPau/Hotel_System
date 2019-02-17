package com.epam.hotel.services;

import com.epam.hotel.daos.RequestDao;
import com.epam.hotel.daos.ReservedRoomDao;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ReservedRoomServiceImpl implements ReservedRoomService {
    private final ReservedRoomDao reservedRoomDao;
    private final RequestDao requestDao;

    public ReservedRoomServiceImpl(ReservedRoomDao reservedRoomDao, RequestDao requestDao) {
        this.reservedRoomDao = reservedRoomDao;
        this.requestDao = requestDao;
    }

    private void reservedRoomtValidation(ReservedRoom reservedRoom) {
        if (reservedRoom.getRequestId() == 0) throw new RuntimeException("request doesn't exist");
        if (reservedRoom.getRoomNumber() == 0) throw new RuntimeException("room doesn't exist");
    }

    @Override
    public List<ReservedRoom> getReservationsOfUser(User user) {
        return getReservationsByUserId(user.getId());
    }

    @Override
    public List<ReservedRoom> getReservationsByUserId(long userId) {
        List<Long> requestsOfUser = requestDao.getAll().stream().filter(s -> s.getUserID() == userId).
                map(s -> s.getRequestID()).collect(Collectors.toList());
        return reservedRoomDao.getAll().stream().
                filter(s -> requestsOfUser.contains(s.getRequestId())).collect(Collectors.toList());

    }

    @Override
    public ReservedRoom create(ReservedRoom reservedRoom) {
        reservedRoomtValidation(reservedRoom);
        return reservedRoomDao.create(reservedRoom);
    }

    @Override
    public boolean delete(long id) {
        return reservedRoomDao.delete(id);
    }

    @Override
    public boolean update(ReservedRoom reservedRoom) {
        reservedRoomtValidation(reservedRoom);
        return reservedRoomDao.update(reservedRoom);
    }

    @Override
    public List<ReservedRoom> getAll() {
        return reservedRoomDao.getAll();
    }

    @Override
    public ReservedRoom getById(long id) {
        ReservedRoom reservedRoom = reservedRoomDao.getById(id);
        if (reservedRoom != null) return reservedRoom;
        else throw new RuntimeException("There is no reserved rooms with this ID");
    }
}
