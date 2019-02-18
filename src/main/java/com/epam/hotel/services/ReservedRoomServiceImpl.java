package com.epam.hotel.services;

import com.epam.hotel.daos.RequestDao;
import com.epam.hotel.daos.ReservedRoomDao;
import com.epam.hotel.daos.RoomDao;
import com.epam.hotel.dtos.ProcessedRequestDto;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.Room;
import com.epam.hotel.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ReservedRoomServiceImpl implements ReservedRoomService {
    private final ReservedRoomDao reservedRoomDao;
    private final RequestDao requestDao;
    private final RoomDao roomDao;
    private final RequestService requestService;

    public ReservedRoomServiceImpl(ReservedRoomDao reservedRoomDao, RequestDao requestDao, RoomDao roomDao, RequestService requestService) {
        this.reservedRoomDao = reservedRoomDao;
        this.requestDao = requestDao;
        this.roomDao = roomDao;
        this.requestService = requestService;
    }

    private void reservedRoomValidation(ReservedRoom reservedRoom) {
        if (reservedRoom.getRequestId() == 0) throw new RuntimeException("request doesn't exist");
        if (reservedRoom.getRoomId() == 0) throw new RuntimeException("room doesn't exist");
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
                .filter(s -> requestsOfUser.contains(s.getRequestId()))
                .collect(Collectors.toList());
    }

    @Override
    public ProcessedRequestDto getProcessedRequestDto(ReservedRoom reservedRoom) {
        Request request = requestDao.getById(reservedRoom.getRequestId());
        Room room = roomDao.getById(reservedRoom.getRoomId());
        ProcessedRequestDto processedRequestDto = new ProcessedRequestDto(request, room);
        return processedRequestDto;
    }

    @Override
    public List<ProcessedRequestDto> getProcessedRequestDtoList(List<ReservedRoom> reservedRooms) {
        return reservedRooms.stream().map(this::getProcessedRequestDto).collect(Collectors.toList());
    }

    @Override
    public List<ProcessedRequestDto> getAllProcessedRequestDtoOfUser(User user) {
        List<ReservedRoom> reservationsOfUser = getReservationsOfUser(user);
        return getProcessedRequestDtoList(reservationsOfUser);
    }

    @Override
    public List<RequestDto> getAllUnprocessedRequestDtoOfUser(User user) {
        List<ReservedRoom> ids = this.getReservationsOfUser(user);
        List<RequestDto> requestList = requestService.getUserRequestsDto(user.getId());
        List<Long> collect = ids.stream().map(ReservedRoom::getRequestId).collect(Collectors.toList());
        return requestList.stream().filter(x -> (!collect.contains(x.getRequestID()))).collect(Collectors.toList());
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
        else throw new RuntimeException("There is no reserved rooms with this ID");
    }

}
