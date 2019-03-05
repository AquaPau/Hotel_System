package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.Room;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceImplTest {

    @Mock
    RoomRepository roomRepository;

    @Mock
    ReservationServiceImpl reservationService;

    RoomServiceImpl roomServiceImpl;

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

    }

}