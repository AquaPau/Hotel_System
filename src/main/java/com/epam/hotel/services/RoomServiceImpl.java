package com.epam.hotel.services;

import com.epam.hotel.Exceptions.RoomNumberAlreadyExistsException;
import com.epam.hotel.daos.ReservedRoomDao;
import com.epam.hotel.daos.RoomDao;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.dtos.RoomDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.Room;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RoomServiceImpl implements RoomService {

    private final RoomDao roomDao;
    private final ReservedRoomService reservedRoomService;

    public RoomServiceImpl(RoomDao roomDao, ReservedRoomService reservedRoomService) {
        this.roomDao = roomDao;
        this.reservedRoomService = reservedRoomService;
    }

    private void roomValidation(Room room) {
        if (room.getCapacity() == null) room.setCapacity(Capacity.SINGLE);
        if (room.getClassID() == null) room.setClassID(ClassID.STANDARD);
        if (room.getPrice() == null) room.setPrice(new BigDecimal(0));
    }

    @Override
    public Room create(Room room) {
        roomValidation(room);
        if (roomDao.getByRoomNumber(room.getRoomNumber()) != null) {
            throw new RoomNumberAlreadyExistsException("The room already exists");
        }
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

    @Override
    public List<RoomDto> getAllRoomsDto() {
        List<Room> allDtoRooms = getAll();
        return allDtoRooms.stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @Override
    public List<RoomDto> getAllFittingRoomsDto(Request request) {
        return getAllFittingRooms(request).stream().map(RoomDto::new).collect(Collectors.toList());
/*        List<RoomDto>  resultList =

        *//*String cap = request.getCapacity().name();
        String classID = request.getClassID().name();
        List<RoomDto> fittingRooms = allFittingRooms.stream().
                filter(room -> room.getCapacity().equals(cap)).
                filter(room -> room.getClassID().equals(classID)).collect(Collectors.toList());
        List<RoomDto> bookedRooms = new ArrayList<>();
        for (RoomDto room : fittingRooms) {
            Integer roomNumber = room.getRoomNumber();
            List<Request> getAllRequestsByRoomNumber = roomDao.getRequestsByRoomNumber(roomNumber);
            for (Request e : getAllRequestsByRoomNumber) {
                if (compareRequestsByTime(request, e)) bookedRooms.add(room);
            }
        }*//*
        return fittingRooms.stream().filter(room -> !bookedRooms.contains(room)).collect(Collectors.toList());*/
    }

    private List<Room> getAllFittingRooms(Request request){
        List<Room> notAvailableByTimeRooms = reservedRoomService.getReservedRoomsForTheTimeOfRequest(request).stream().
                map(c -> roomDao.getById(c.getRoomID())).collect(Collectors.toList());
        List<Room> availableByTimeAndCapacityRooms = getAll().stream().filter(c -> (!notAvailableByTimeRooms.contains(c)) &&
                c.getCapacity() == request.getCapacity()).collect(Collectors.toList());
        List<Room> mostsuitableList = availableByTimeAndCapacityRooms.stream().filter(c ->
                c.getClassID() == request.getClassID()).collect(Collectors.toList());
        List<Room> lessSuitableList = (availableByTimeAndCapacityRooms.stream().filter(c ->
                c.getClassID() != request.getClassID()).collect(Collectors.toList()));
        mostsuitableList.addAll(lessSuitableList);
    return mostsuitableList;
    }



    @Override
    public RoomDto getRoomDto(Room room) {
        roomValidation(room);
        return new RoomDto(room);
    }

    @Override
    public Room getRoomFromDto(RoomDto roomDto) {
        return roomDto.getRoom();
    }

    @Override
    public List<RoomDto> getRoomDtoList() {
        List<Room> roomList = getAll();
        return roomList.stream().map(RoomDto::new).sorted().collect(Collectors.toList());
    }


}
