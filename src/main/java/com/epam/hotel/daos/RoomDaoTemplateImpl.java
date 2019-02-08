package com.epam.hotel.daos;

import com.epam.hotel.enums.ClassID;
import com.epam.hotel.model.Room;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import com.epam.hotel.enums.*;

public class RoomDaoTemplateImpl implements RoomDao {

    private final JdbcTemplate jdbcTemplate;
    private final String SQL_CHECK_IF_EXISTS_BY_ID = "SELECT COUNT(*) FROM hotel.rooms WHERE roomid = ?";
    private final String SQL_CHECK_IF_EXISTS_BY_ROOM_NUMBER = "SELECT COUNT(*) FROM hotel.rooms WHERE roomnumber = ?";
    private final String SQL_GET_ROOM_COUNT = "SELECT COUNT(*) FROM hotel.Rooms";
    private final String SQL_GET_ALL_ROOMS = "SELECT * FROM hotel.Rooms";
    private final String SQL_UPDATE_ROOM = "UPDATE hotel.rooms SET roomnumber = ?, " +
            "classid = CAST(? AS hotel.classid), capacity = CAST(? AS hotel.capacity), price = ? WHERE roomid = ?";
    private final String SQL_CREATE_NEW_ROOM = "INSERT INTO hotel.rooms (roomnumber, classid, " +
            "capacity, price) VALUES (?, CAST(? AS hotel.classid), CAST(? AS hotel.capacity), ?)";
    private final String SQL_DELETE_ROOM = "DELETE FROM hotel.rooms WHERE roomid = ?";
    private final String SQL_GET_BY_ID = "SELECT * FROM hotel.rooms WHERE roomid = ?";
    private final String SQL_GET_BY_ROOM_NUMBER = "SELECT * FROM hotel.rooms WHERE roomnumber = ?";

    public RoomDaoTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Integer checkIfExists(long id) {
        Integer count = jdbcTemplate.queryForObject(SQL_CHECK_IF_EXISTS_BY_ID, new Object[]{id}, Integer.class);
        return count;
    }

    public Integer checkIfNumberExists(int roomNumber) {
        Integer count = jdbcTemplate.queryForObject(SQL_CHECK_IF_EXISTS_BY_ROOM_NUMBER,new Object[]{roomNumber}, Integer.class);
        return count;
    }

    public int getRoomCount() {
        return jdbcTemplate.queryForObject(SQL_GET_ROOM_COUNT, Integer.class);
    }

    @Override
    public Room create(Room room) {
        jdbcTemplate.update(SQL_CREATE_NEW_ROOM, new Object[] {room.getRoomNumber(), room.getClassID(),
                room.getCapacity(), room.getPrice()});
        Room newroom = null;
        if (this.checkIfNumberExists(room.getRoomNumber()) > 0) {
            newroom = this.getByRoomNumber(room.getRoomNumber());
        }
        return newroom;
    }

    @Override

    public boolean update(Room room) {
        boolean isUpdated = false;
        if (this.checkIfExists(room.getRoomID()) > 0) {
            jdbcTemplate.update(SQL_UPDATE_ROOM, new Object[]{room.getRoomNumber(), room.getClassID(),
                    room.getCapacity(), room.getPrice(), room.getRoomID()});
            isUpdated = true;
        }
        return isUpdated;
    }


    @Override
    public boolean delete(long id) {
        boolean isDeleted = false;
        if (this.checkIfExists(id) > 0) {
            jdbcTemplate.update(SQL_DELETE_ROOM, new Object[]{id});
            isDeleted = true;
        }
        return isDeleted;
    }


    @Override
    public List<Room> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL_ROOMS, (rs, rowNum) -> {
            Room room = new Room();
            room.setRoomID(rs.getInt(1));
            room.setRoomNumber(rs.getInt(2));
            room.setClassID(ClassID.valueOf(rs.getString(3)));
            room.setCapacity(Capacity.valueOf(rs.getString(4)));
            room.setPrice(rs.getBigDecimal(5));
            return room;
        });
    }

    @Override
    public Room getById(long id) {
        Room room = null;
        if (this.checkIfExists(id) > 0) {
            room = (Room) jdbcTemplate.queryForObject(
                    SQL_GET_BY_ID, new Object[]{ id }, Room.class);
        }
        return room;
    }

    @Override
    public Room getByRoomNumber(int roomNumber) {
        Room room = null;
        if (this.checkIfNumberExists(roomNumber) > 0) {
            room = (Room) jdbcTemplate.queryForObject(
                    SQL_GET_BY_ROOM_NUMBER, new Object[] { roomNumber }, Room.class);
        }
        return room;
    }

}