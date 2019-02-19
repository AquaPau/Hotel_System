package com.epam.hotel.daos;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.Room;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReservedRoomDaoTemplateImplTest {
    private static ReservedRoomDao reservedRoomDao;
    private static RequestDao requestDao;
    private static RoomDao roomDao;
    private static JdbcTemplate jdbcTemplate;
    private Request request;
    private Room room;
    private ReservedRoom reservedRoom;

    @BeforeClass
    public static void setUp() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
        jdbcTemplate = context.getBean("testJdbcTemplate", JdbcTemplate.class);
        reservedRoomDao = new ReservedRoomDaoTemplateImpl(jdbcTemplate);
        roomDao = new RoomDaoTemplateImpl(jdbcTemplate);
        requestDao = new RequestDaoTemplateImpl(jdbcTemplate);
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("test-database-create.sql"));
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("test-database-drop.sql"));

    }

    @Before
    public void set() throws SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("test-database-create.sql"));
        room = this.createTestRoom(0);
        request = this.createTestRequest();
        reservedRoom = this.createTestReservation();
    }

    @After
    public void tearDown() throws SQLException {
        ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(),
                new ClassPathResource("test-database-drop.sql"));
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

        return requestDao.create(request);
    }

    private Room createTestRoom(int roomnumber) {
        Room room = new Room(roomnumber, ClassID.STANDARD, Capacity.SINGLE, BigDecimal.valueOf(1000));
        return roomDao.create(room);
    }

    private ReservedRoom createTestReservation() {
        ReservedRoom reservedRoom = new ReservedRoom();
        reservedRoom.setRequestID(1);
        reservedRoom.setRoomID(1);
        return reservedRoom;
    }

    @Test
    public void create() {
        reservedRoomDao.create(reservedRoom);
        assertEquals(reservedRoom.getReservedRoomID(), 1);
    }

    @Test
    public void delete() {
        reservedRoomDao.create(reservedRoom);
        boolean isDeleted = reservedRoomDao.delete(reservedRoom.getReservedRoomID());
        assertTrue(isDeleted);
    }

    @Test
    public void update() {
        reservedRoomDao.create(reservedRoom);
        Request request = createTestRequest();
        reservedRoom.setRequestID(2);
        boolean isUpdated = reservedRoomDao.update(reservedRoom);
        assertTrue(isUpdated);
    }

    @Test
    public void getAll() {
        Room room1 = createTestRoom(1);
        Room room2 = createTestRoom(2);

        Request request1 = createTestRequest();
        Request request2 = createTestRequest();

        ReservedRoom reservedRoom1 = new ReservedRoom();
        reservedRoom1.setRequestID(1);
        reservedRoom1.setRoomID(1);

        ReservedRoom reservedRoom2 = new ReservedRoom();
        reservedRoom2.setRoomID(2);
        reservedRoom2.setRequestID(2);

        List<ReservedRoom> reservedRoomList = Arrays.asList(reservedRoom1, reservedRoom2);

        ReservedRoom reservedRoom1FromDao = reservedRoomDao.create(reservedRoom1);
        ReservedRoom reservedRoom2FromDao = reservedRoomDao.create(reservedRoom2);

        List<ReservedRoom> reservedRooms = reservedRoomDao.getAll();

        assertEquals(reservedRoomList, reservedRooms);
    }

    @Test
    public void getById() {
        reservedRoomDao.create(reservedRoom);
        ReservedRoom receivedReservedRoom = reservedRoomDao.getById(reservedRoom.getReservedRoomID());
        assertEquals(reservedRoom, receivedReservedRoom);
    }
}