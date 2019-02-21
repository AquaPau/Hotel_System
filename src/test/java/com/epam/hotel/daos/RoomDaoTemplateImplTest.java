package com.epam.hotel.daos;

import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.Room;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class RoomDaoTemplateImplTest {

    private static RoomDao roomDao;
    private static JdbcTemplate template;

    @BeforeClass
    public static void setUp() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
        template = context.getBean("testJdbcTemplate", JdbcTemplate.class);
        roomDao = new RoomDaoTemplateImpl(template);
        ScriptUtils.executeSqlScript(template.getDataSource().getConnection(), new ClassPathResource("test-database-create.sql"));
        ScriptUtils.executeSqlScript(template.getDataSource().getConnection(), new ClassPathResource("test-database-drop.sql"));
    }

    @Before
    public void set() throws SQLException {
        ScriptUtils.executeSqlScript(template.getDataSource().getConnection(), new ClassPathResource("test-database-create.sql"));
    }

    @After
    public void after() throws SQLException {
        ScriptUtils.executeSqlScript(template.getDataSource().getConnection(), new ClassPathResource("test-database-drop.sql"));
    }

    private Room createTestRoom() {
        return new Room(1, ClassID.STANDARD, Capacity.SINGLE, BigDecimal.valueOf(1000));
    }

    @Test
    public void create() {
        Room testRoom = createTestRoom();
        roomDao.create(testRoom);
        assertEquals(testRoom.getRoomNumber(), 1);
    }

    @Test
    public void getById() {
        Room testRoom = createTestRoom();
        roomDao.create(testRoom);
        Room createdRoom = roomDao.getById(1);
        assertEquals(createdRoom.getRoomID(), 1);
    }

    @Test
    public void getByRoomNumber() {
        Room testRoom = createTestRoom();
        roomDao.create(testRoom);
        Room createdRoom = roomDao.getByRoomNumber(testRoom.getRoomNumber());
        assertEquals(createdRoom.getRoomNumber(), testRoom.getRoomNumber());
    }

    @Test
    public void update() {
        Room testRoom = roomDao.create(createTestRoom());
        testRoom.setRoomNumber(2);
        roomDao.update(testRoom);
        assertEquals(testRoom.getRoomNumber(), 2);
    }

    @Test
    public void delete() {
        roomDao.create(createTestRoom());
        assertTrue(roomDao.delete(1L));
    }

    @Test
    public void getAll() {
        Room testRoom = createTestRoom();
        roomDao.create(testRoom);
        testRoom.setRoomNumber(2);
        roomDao.create(testRoom);
        List<Room> roomList = roomDao.getAll();
        assertEquals(2, roomList.size());
    }

    @Test
    public void getAvailableRoomsInPeriodAndCapacity() {

    }
}

