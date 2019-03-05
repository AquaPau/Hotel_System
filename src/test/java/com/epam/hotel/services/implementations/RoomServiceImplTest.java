package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.Room;
import com.epam.hotel.domains.enums.Capacity;
import com.epam.hotel.domains.enums.ClassID;
import com.epam.hotel.repositories.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceImplTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    ReservationServiceImpl reservationService;

    RoomServiceImpl roomServiceImpl;

    private static Request makeTestRequest() {
        Request request = new Request();
        request.setCheckIn(new Date());
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt); c.add(Calendar.DATE, 1);
        dt = c.getTime();
        request.setCheckOut(dt);
        request.setCapacity(Capacity.SINGLE);
        request.setClassID(ClassID.STANDARD);
        request.setId(1);
        return request;
    }

    private static Room makeTestRoom(int number) {
        Room room = new Room();
        room.setCapacity(Capacity.SINGLE);
        room.setClassID(ClassID.STANDARD);
        room.setNumber(number);
        room.setId(number);
        room.setPrice(BigDecimal.valueOf(100l));
        return room;
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        roomServiceImpl = new RoomServiceImpl(roomRepository, reservationService);
    }

    @Test
    void findById() {
        Optional<Room> testRoom = Optional.of(new Room());
        when(roomRepository.findById(anyLong())).thenReturn(testRoom);
        Room foundRoom = roomServiceImpl.findById(1l);
        assertEquals(foundRoom, testRoom.get());
    }

    @Test
    void findByNotExistingId() {
        Optional<Room> testRoom = Optional.empty();
        when(roomRepository.findById(anyLong())).thenReturn(testRoom);
        Assertions.assertThrows(RuntimeException.class, () -> roomServiceImpl.findById(1l));
    }

    @Test
    void findAllRoomsPaged() {
        List<Room> testList = new ArrayList<>();
        testList.add(new Room()); testList.add(new Room());
        Page<Room> testPage = new PageImpl<Room>(testList);
        when(roomRepository.findAll(PageRequest.of(0, 2, Sort.Direction.ASC, "id"))).thenReturn(testPage);
        Page<Room> foundPage = roomServiceImpl.findAllRoomsPaged(1,2);
        assertEquals(foundPage, testPage);
    }

    @Test
    void findAllRoomsAvailableForRequest() {
        List<Room> testList = new ArrayList<>();
        List<Room> nonEmptyRooms = new ArrayList<>();
        Room testRoom1 = makeTestRoom(1);
        Room testRoom2 = makeTestRoom(2);
        Room testRoom3 = makeTestRoom(3);
        testList.add(testRoom1); testList.add(testRoom2); testList.add(testRoom3);
        nonEmptyRooms.add(testRoom3);
        Request request = makeTestRequest();
        when(roomRepository.findAll().stream().filter(x -> !nonEmptyRooms.
                contains(x)).
                collect(Collectors.toList())).
                thenReturn(testList);
        int max = 2 > testList.size() ? testList.size() : 2;
        Page<Room> testPage = new PageImpl<Room>(testList);
        Page<Room> foundPage = roomServiceImpl.findAllRoomsAvailableForRequest(request,1,2);
        assertEquals(testPage.getTotalElements(), foundPage.getTotalElements());

    }

}