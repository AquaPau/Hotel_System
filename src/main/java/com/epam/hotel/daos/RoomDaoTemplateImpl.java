package com.epam.hotel.daos;

import com.epam.hotel.enums.ClassID;
import com.epam.hotel.model.Room;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import com.epam.hotel.enums.*;

public class RoomDaoTemplateImpl implements RoomDao {

    private final JdbcTemplate jdbcTemplate;

    public RoomDaoTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Integer checkIfExists(long id) {
        String check = "SELECT COUNT(*) FROM hotel.rooms WHERE roomid = ?";
        Integer count = jdbcTemplate.queryForObject(check, new Object[]{id}, Integer.class);
        return count;
    }

    public Integer checkIfNumberExists(int roomNumber) {
        String check = "SELECT COUNT(*) FROM hotel.rooms WHERE roomnumber = ?";
        Integer count = jdbcTemplate.queryForObject(check, new Object[]{roomNumber}, Integer.class);
        return count;
    }

    public int getRoomCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hotel.Rooms", Integer.class);
    }

    @Override
    public Room create(Room room) {
        int roomNumber = room.getRoomNumber();
        String classID = room.getClassID().name();
        String cap = room.getCapacity().name();
        String price = room.getPrice().toString();
        String sql = String.format("INSERT INTO hotel.rooms (roomnumber, classid, " +
                "capacity, price) VALUES (%d,'%s','%s', %s)", roomNumber, classID, cap, price);
        jdbcTemplate.execute(sql);
        Room newroom = null;
        if (this.checkIfNumberExists(roomNumber) > 0) {
            newroom = room;
        }
        return newroom;
    }

    @Override

    public boolean update(Room room) {
        boolean isUpdated = false;
        if (this.checkIfExists(room.getRoomID()) > 0) {
            String sql = "UPDATE hotel.rooms SET roomnumber = ?, " +
                    "classid = CAST(? AS hotel.classid), capacity = CAST(? AS hotel.capacity), price = ? WHERE roomid = ?";
            jdbcTemplate.update(sql, new Object[]{room.getRoomNumber(), room.getClassID(),
                    room.getCapacity(), room.getPrice(), room.getRoomID()});
            isUpdated = true;
        }
        return isUpdated;
    }


    @Override
    public boolean delete(long id) {
        boolean isDeleted = false;
        if (this.checkIfExists(id) > 0) {
            String sql = "DELETE FROM hotel.rooms WHERE roomid = ?";
            jdbcTemplate.update(sql, new Object[]{id});
            isDeleted = true;
        }
        return isDeleted;
    }


    @Override
    public List<Room> getAll() {
        return jdbcTemplate.query("SELECT * FROM hotel.Rooms", (rs, rowNum) -> {
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
            String sql = "SELECT * FROM hotel.rooms WHERE roomid = ?";
            room = (Room) jdbcTemplate.queryForObject(
                    sql, new Object[]{ id }, Room.class);
        }
        return room;
    }

    @Override
    public Room getByRoomNumber(int roomNumber) {
        Room room = null;
        if (this.checkIfNumberExists(roomNumber) > 0) {
            String sql = "SELECT * FROM hotel.rooms WHERE roomnumber = ?";
            room = (Room) jdbcTemplate.queryForObject(
                    sql, new Object[] { roomNumber }, Room.class);
        }
        return room;
    }

}