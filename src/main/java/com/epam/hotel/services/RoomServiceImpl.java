package com.epam.hotel.services;

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

    public RoomServiceImpl(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    private void roomValidation(Room room) {
        if (room.getCapacity() == null) room.setCapacity(Capacity.SINGLE);
        if (room.getClassID() == null) room.setClassID(ClassID.STANDARD);
        if (room.getPrice() == null) room.setPrice(new BigDecimal(0));
    }

    public BigDecimal calculateRoomPrice(BigDecimal baseprice, double classMultiplier, double capMultiplier) {
        MathContext mc = new MathContext(2);
        return baseprice.multiply(BigDecimal.valueOf(capMultiplier * classMultiplier), mc);
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
    public List<Room> getAll() { return roomDao.getAll(); }

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
        List<RoomDto> allFittingRooms = getAllRoomsDto();
        String cap = request.getCapacity().name();
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
        }
        return fittingRooms.stream().filter(room -> !bookedRooms.contains(room)).collect(Collectors.toList());
    }

    private boolean compareRequestsByTime(Request req1, Request req2) {
        if (req1.getCheckOut().getTime() > req2.getCheckIn().getTime() && req1.getCheckOut().getTime() < req1.getCheckOut().getTime()) {
            return true;
        } else if (req1.getCheckIn().getTime() > req2.getCheckIn().getTime() && req1.getCheckIn().getTime() < req1.getCheckOut().getTime()) {
            return true;
        }
        return false;
    }

    public void addToReservedRooms(Request request, Room room) {
        roomDao.addToReservedRooms(request, room);
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
    public List<RoomDto> getRoomDtoList(){
        List<Room> roomList = getAll();
        return roomList.stream().map(RoomDto::new).sorted().collect(Collectors.toList());
    }


}
