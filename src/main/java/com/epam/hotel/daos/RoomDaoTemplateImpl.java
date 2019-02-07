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

    public void addRoom(Room room) {
        int roomNumber = room.getRoomNumber();
        String classID = room.getClassID().name();
        String cap = room.getCapacity().name();
        String price = room.getPrice().toString();
        String query = String.format("INSERT INTO (roomnumber, classid, " +
                "capacity, price) hotel.rooms VALUES (%d,'%s','%s', %s)", roomNumber, classID, cap, price);
        jdbcTemplate.execute(query);
    }

    public int getRoomCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hotel.Rooms", Integer.class);
    }

    public List<Room> getRoomList() {
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
    public Room create(Room entity) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Room entity) {
        return false;
    }

    @Override
    public List<Room> getAll() {
        return null;
    }

    @Override
    public Room getById(int id) {
        return null;
    }
}