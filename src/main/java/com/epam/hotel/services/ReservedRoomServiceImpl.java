package com.epam.hotel.services;

import com.epam.hotel.Exceptions.RequestNotExistException;
import com.epam.hotel.Exceptions.ReservationNotExistException;
import com.epam.hotel.Exceptions.RoomNotExistException;
import com.epam.hotel.daos.RequestDao;
import com.epam.hotel.daos.ReservedRoomDao;
import com.epam.hotel.daos.RoomDao;
import com.epam.hotel.dtos.ProcessedRequestDto;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.Room;
import com.epam.hotel.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ReservedRoomServiceImpl implements ReservedRoomService {
    private final ReservedRoomDao reservedRoomDao;
    private final RequestDao requestDao;
    private final RoomDao roomDao;

    public ReservedRoomServiceImpl(ReservedRoomDao reservedRoomDao, RequestDao requestDao, RoomDao roomDao) {
        this.reservedRoomDao = reservedRoomDao;
        this.requestDao = requestDao;
        this.roomDao = roomDao;
    }

    private void reservedRoomValidation(ReservedRoom reservedRoom) {
        if (reservedRoom.getRequestID() == 0) throw new RequestNotExistException("request doesn't exist");
        if (reservedRoom.getRoomID() == 0) throw new RoomNotExistException("room doesn't exist");
    }

    @Override
    public List<ReservedRoom> getReservationsOfUser(User user) {
        return getReservationsByUserId(user.getId());
    }

    @Override
    public List<ReservedRoom> getReservationsByUserId(long userId) {
        List<Long> requestsOfUser = requestDao.getAll().stream()
                .filter(s -> s.getUserID() == userId)
                .map(Request::getRequestID)
                .collect(Collectors.toList());
        return reservedRoomDao.getAll().stream()
                .filter(s -> requestsOfUser.contains(s.getRequestID()))
                .collect(Collectors.toList());
    }

    @Override
    public ProcessedRequestDto getProcessedRequestDto(ReservedRoom reservedRoom) {
        Request request = requestDao.getById(reservedRoom.getRequestID());
        Room room = roomDao.getById(reservedRoom.getRoomID());
        return new ProcessedRequestDto(request, room, reservedRoom.getReservedRoomID());
    }

    @Override
    public List<ProcessedRequestDto> getAllProcessedRequestDtoOfUser(User user) {
        List<ReservedRoom> reservationsOfUser = getReservationsOfUser(user);
        return reservationsOfUser.stream()
                .map(this::getProcessedRequestDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<RequestDto> getAllUnprocessedRequestDtoOfUser(User user, List<RequestDto> requestList) {
        List<ReservedRoom> ids = this.getReservationsOfUser(user);
        List<Long> collect = ids.stream().map(ReservedRoom::getRequestID).collect(Collectors.toList());
        return requestList.stream()
                .filter(x -> (!collect.contains(x.getRequestID())))
                .collect(Collectors.toList());
    }

    @Override
    public ProcessedRequestDto getProcessedRequestDto(Request request) {
        long userID = request.getUserID();
        ReservedRoom reservedRoom = getReservationsByUserId(userID).stream()
                .filter(x -> x.getRequestID() == request.getRequestID())
                .collect(Collectors.toSet())
                .stream()
                .findFirst()
                .get();
        Room room = roomDao.getById(reservedRoom.getRoomID());
        return new ProcessedRequestDto(request, room, reservedRoom.getReservedRoomID());
    }

    @Override
    public List<ProcessedRequestDto> getProcessedRequestDtoList(List<Request> requestList) {
        return requestList.stream()
                .map(this::getProcessedRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservedRoom create(ReservedRoom reservedRoom) {
        reservedRoomValidation(reservedRoom);
        return reservedRoomDao.create(reservedRoom);
    }

    @Override
    public boolean delete(long id) {
        return reservedRoomDao.delete(id);
    }

    @Override
    public boolean update(ReservedRoom reservedRoom) {
        reservedRoomValidation(reservedRoom);
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
        else throw new ReservationNotExistException("There is no reserved rooms with this ID");
    }

    @Override
    public void cancelReservation(long id) {
        ReservedRoom reservedRoom = reservedRoomDao.getById(id);
        long requestID = reservedRoom.getRequestID();
        reservedRoomDao.delete(id);
        requestDao.delete(requestID);
    }

}

