package com.epam.hotel.services;

import com.epam.hotel.daos.RequestDao;
import com.epam.hotel.daos.ReservedRoomDao;
import com.epam.hotel.daos.RoomDao;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.User;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    ReservedRoomService reservedRoomService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        reservedRoomService = new ReservedRoomServiceImpl(reservedRoomDao, requestDao, roomDao);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getReservationsOfUser() {
        User user;

       // reservedRoomService.getReservationsOfUser(user);
    }

    @Test
    public void getReservationsByUserId() {
    }

    @Test
    public void getProcessedRequestDto() {
    }

    @Test
    public void getAllProcessedRequestDtoOfUser() {
    }

    @Test
    public void getAllUnprocessedRequestDtoOfUser() {
    }

    @Test
    public void create() {
        ReservedRoom reservedRoom = new ReservedRoom();
        reservedRoom.setRequestID(1);
        reservedRoom.setRoomID(1);
        reservedRoom.setReservedRoomID(1);
        when(reservedRoomDao.create(any())).thenReturn(reservedRoom);

        ReservedRoom savedRoom = reservedRoomService.create(reservedRoom);

        assertEquals(savedRoom, reservedRoom);
        verify(reservedRoomDao, times(1)).create(any(ReservedRoom.class));
    }


    @Test
    public void cancelReservation() {

        when(reservedRoomDao.delete(anyLong())).thenReturn(true);
        when(requestDao.delete(anyLong())).thenReturn(true);
        when(reservedRoomDao.getById(anyLong())).thenReturn(createTestReservedRoom());

        reservedRoomService.cancelReservation(1L);

        verify(reservedRoomDao, times(1)).getById(anyLong());
        verify(reservedRoomDao, times(1)).delete(anyLong());
        verify(requestDao, times(1)).delete(anyLong());
    }

    private ReservedRoom createTestReservedRoom() {
        ReservedRoom reservedRoom = new ReservedRoom();
        reservedRoom.setReservedRoomID(1);
        reservedRoom.setRequestID(1);
        reservedRoom.setRoomID(1);
        return reservedRoom;
    }

    private Request createTestRequest() {
        Request request = new Request();
        request.setUserID(1L);
        request.setCapacity(Capacity.SINGLE);
        request.setClassID(ClassID.STANDARD);
        request.setPaymentStatus(PaymentStatus.NOBILL);
        request.setCheckIn(Timestamp.valueOf(LocalDateTime.of(2018, 1, 1, 1, 0,
                0, 0)));
        request.setCheckOut(Timestamp.valueOf(LocalDateTime.of(2018, 1, 2, 1, 0,
                0, 0)));
        return request;
    }
}