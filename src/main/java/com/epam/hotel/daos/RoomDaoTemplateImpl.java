package com.epam.hotel.daos;

import com.epam.hotel.enums.ClassID;
import com.epam.hotel.model.Room;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.epam.hotel.enums.*;
import org.springframework.jdbc.core.RowMapper;

public class RoomDaoTemplateImpl implements RoomDao {
    
    private final JdbcTemplate jdbcTemplate;
    private final String SQL_CHECK_IF_EXISTS_BY_ID = "SELECT COUNT(*) FROM hotel.rooms WHERE roomid = ?";
    private final String SQL_CHECK_IF_EXISTS_BY_ROOM_NUMBER = "SELECT COUNT(*) FROM hotel.rooms WHERE roomnumber = ?";
    private final String SQL_GET_ALL_ROOMS = "SELECT roomid, roomnumber, classid, capacity, price FROM hotel.Rooms";
    private final String SQL_UPDATE_ROOM = "UPDATE hotel.rooms SET roomnumber = ?, classid = ?, capacity = ?, price = ? WHERE roomid = ?";
    private final String SQL_CREATE_NEW_ROOM = "INSERT INTO hotel.rooms (roomnumber, classid, capacity, price) VALUES (?, ?, ?, ?)";
    private final String SQL_DELETE_ROOM = "DELETE FROM hotel.rooms WHERE roomid = ?";
    private final String SQL_GET_BY_ID = "SELECT roomid, roomnumber, classid, capacity, price FROM hotel.rooms WHERE roomid = ?";
    private final String SQL_GET_BY_ROOM_NUMBER = "SELECT roomid, roomnumber, classid, capacity, price FROM hotel.rooms WHERE roomnumber = ?";

    public RoomDaoTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Integer checkIfExists(long id) {
        return jdbcTemplate.queryForObject(SQL_CHECK_IF_EXISTS_BY_ID, new Object[]{id}, Integer.class);
    }

    private Integer checkIfNumberExists(int roomNumber) {
        return jdbcTemplate.queryForObject(SQL_CHECK_IF_EXISTS_BY_ROOM_NUMBER, new Object[]{roomNumber}, Integer.class);
    }

    @Override
    public Room create(Room room) {
        jdbcTemplate.update(SQL_CREATE_NEW_ROOM, room.getRoomNumber(), room.getClassID().toString(),
                room.getCapacity().toString(), room.getPrice());
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
            jdbcTemplate.update(SQL_UPDATE_ROOM, room.getRoomNumber(), room.getClassID().name(),
                    room.getCapacity().name(), room.getPrice(), room.getRoomID());
            isUpdated = true;
        }
        return isUpdated;
    }


    @Override
    public boolean delete(long id) {
        boolean isDeleted = false;
        if (this.checkIfExists(id) > 0) {
            jdbcTemplate.update(SQL_DELETE_ROOM, id);
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
            room = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[]{ id }, new RoomRowMapper());
        }
        return room;
    }

    @Override
    public Room getByRoomNumber(int roomNumber) {
        Room room = null;
        if (this.checkIfNumberExists(roomNumber) > 0) {
            room = jdbcTemplate.queryForObject(SQL_GET_BY_ROOM_NUMBER, new Object[] { roomNumber }, new RoomRowMapper());
        }
        return room;
    }

    class RoomRowMapper implements RowMapper<Room> {
        @Override
        public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
            Room room = new Room();
            room.setRoomID(rs.getLong("roomid"));
            room.setRoomNumber(rs.getInt("roomnumber"));
            room.setClassID(ClassID.valueOf(rs.getString("classid")));
            room.setCapacity(Capacity.valueOf(rs.getString("capacity")));
            room.setPrice(rs.getBigDecimal("price"));
            return room;
        }
    }

}