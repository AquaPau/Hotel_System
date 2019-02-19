package com.epam.hotel.services;

import com.epam.hotel.daos.RequestDao;
import com.epam.hotel.daos.ReservedRoomDao;
import com.epam.hotel.daos.RoomDao;
import com.epam.hotel.dtos.ProcessedRequestDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.Room;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-test.xml")
public class ReservedRoomServiceImplTest {

    @Mock
    ReservedRoomDao reservedRoomDao;
    @Mock
    RequestDao requestDao;
    @Mock
    RoomDao roomDao;

    private ReservedRoomService reservedRoomService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        reservedRoomService = new ReservedRoomServiceImpl(reservedRoomDao, requestDao, roomDao);
    }

    @Test
    public void getReservationsByUserId() {
        Request request1 = new Request();
        request1.setRequestID(1);
        request1.setUserID(1);
        Request request2 = new Request();
        request2.setRequestID(3);
        ReservedRoom reservedRoom1 = new ReservedRoom();
        reservedRoom1.setRequestID(1);
        ReservedRoom reservedRoom2 = new ReservedRoom();
        reservedRoom2.setRequestID(3);
        when(requestDao.getAll()).thenReturn(Arrays.asList(request1, request2));
        when(reservedRoomDao.getAll()).thenReturn(Arrays.asList(reservedRoom1, reservedRoom2));

        List<ReservedRoom> reservationsByUserId = reservedRoomService.getReservationsByUserId(1L);

        verify(requestDao, times(1)).getAll();
        verify(reservedRoomDao, times(1)).getAll();
        assertEquals(reservationsByUserId, Collections.singletonList(reservedRoom1));
    }

    @Test
    public void getProcessedRequestDto() {
        ReservedRoom reservedRoom = new ReservedRoom();
        reservedRoom.setReservedRoomID(1);
        reservedRoom.setRequestID(1);
        reservedRoom.setRoomID(1);
        Request request = createTestRequest(1);
        Room room = new Room();
        room.setRoomID(1);
        room.setPrice(new BigDecimal(10));
        when(requestDao.getById(anyLong())).thenReturn(request);
        when(roomDao.getById(anyLong())).thenReturn(room);

        ProcessedRequestDto processedRequestDto = reservedRoomService.getProcessedRequestDto(reservedRoom);

        verify(requestDao, times(1)).getById(anyLong());
        verify(roomDao, times(1)).getById(anyLong());
        assertEquals(processedRequestDto.getReservationID(), 1);
        assertEquals(processedRequestDto.getRoomId(), 1);
        assertEquals(processedRequestDto.getRequestID(), 1);
        assertEquals(processedRequestDto.getPrice(), 100);
    }

    @Test
    public void create() {
        ReservedRoom reservedRoom = new ReservedRoom();
        reservedRoom.setRequestID(1);
        reservedRoom.setRoomID(1);
        reservedRoom.setReservedRoomID(1);
        when(reservedRoomDao.create(any(ReservedRoom.class))).thenReturn(reservedRoom);

        ReservedRoom savedRoom = reservedRoomService.create(reservedRoom);

        assertEquals(savedRoom, reservedRoom);
        verify(reservedRoomDao, times(1)).create(any(ReservedRoom.class));
    }

    @Test
    public void cancelReservation() {
        ReservedRoom reservedRoom = new ReservedRoom();
        when(reservedRoomDao.delete(anyLong())).thenReturn(true);
        when(requestDao.delete(anyLong())).thenReturn(true);
        when(reservedRoomDao.getById(anyLong())).thenReturn(reservedRoom);

        reservedRoomService.cancelReservation(1L);

        verify(reservedRoomDao, times(1)).getById(anyLong());
        verify(reservedRoomDao, times(1)).delete(anyLong());
        verify(requestDao, times(1)).delete(anyLong());
    }

    private Request createTestRequest(int i) {
        Request request = new Request();
        request.setRequestID(i);
        request.setCapacity(Capacity.values()[i]);
        request.setClassID(ClassID.values()[i]);
        request.setPaymentStatus(PaymentStatus.values()[i]);
        request.setCheckIn(Timestamp.valueOf(LocalDateTime.of(2019, 1, 1, 1, 0,
                0, 0)));
        request.setCheckOut(Timestamp.valueOf(LocalDateTime.of(2019, 1, 11, 1, 0,
                0, 0)));
        return request;
    }

}
