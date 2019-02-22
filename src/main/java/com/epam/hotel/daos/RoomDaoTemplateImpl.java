package com.epam.hotel.daos;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.Room;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.epam.hotel.model.enums.*;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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
    private final String GET_ROOMS_AVAILABLE_IN_PERIOD_AND_CAPACITY = "SELECT rooms.roomid AS roomid, rooms.roomnumber " +
            "AS roomnumber, rooms.capacity as capacity, rooms.classid as classid, rooms.price as price " +
            "FROM hotel.rooms AS rooms FULL JOIN hotel.reservedrooms as res ON rooms.roomid = res.roomid " +
            "WHERE rooms.roomid NOT IN " +
            "(SELECT res.roomid FROM hotel.reservedrooms AS res JOIN hotel.requests AS req " +
            "ON res.requestid = req.requestid " +
            "WHERE " +
            "req.checkin <= :checkin AND req.checkout >= :checkin " +
            "OR " +
            "req.checkin <= :checkout AND req.checkout >= :checkout" +
            ") " +
            "AND rooms.capacity IN (:capacityList) " +
            "ORDER BY CASE\n" +
            "           WHEN classid = :class1 THEN 1\n" +
            "           WHEN classid = :class2 THEN 2\n" +
            "           WHEN classid = :class3 THEN 3\n" +
            "           WHEN classid = :class4 THEN 4\n" +
            "           END,\n" +
            "         CASE\n" +
            "           WHEN capacity = :cap1 THEN 1\n" +
            "           WHEN capacity = :cap2 THEN 2\n" +
            "           WHEN capacity = :cap3 THEN 3\n" +
            "           WHEN capacity = :cap4 THEN 4\n" +
            "           END";


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
    public Room create(Room entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(SQL_CREATE_NEW_ROOM, new String[]{"roomid"});
                    statement.setInt(1, entity.getRoomNumber());
                    statement.setString(2, entity.getClassID().name());
                    statement.setString(3, entity.getCapacity().name());
                    statement.setBigDecimal(4, entity.getPrice());
                    return statement;
                },
                keyHolder);
        entity.setRoomID(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return entity;
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
        return jdbcTemplate.update(SQL_DELETE_ROOM, id) != 0;
    }

    @Override
    public List<Room> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL_ROOMS, new RoomRowMapper());
    }

    @Override
    public Room getById(long id) {
        Room room = null;
        if (this.checkIfExists(id) > 0) {
            room = jdbcTemplate.queryForObject(SQL_GET_BY_ID, new Object[]{id}, new RoomRowMapper());
        }
        return room;
    }

    @Override
    public Room getByRoomNumber(int roomNumber) {
        Room room = null;
        if (this.checkIfNumberExists(roomNumber) > 0) {
            room = jdbcTemplate.queryForObject(SQL_GET_BY_ROOM_NUMBER, new Object[]{roomNumber}, new RoomRowMapper());
        }
        return room;
    }

    @Override
    public List<Room> getAvailableRoomsInPeriodAndCapacity(Request request) {
        NamedParameterJdbcTemplate tempTemplate =
                new NamedParameterJdbcTemplate(Objects.requireNonNull(jdbcTemplate.getDataSource()));

        List<String> capacityList = new ArrayList<>();
        switch (request.getCapacity()) {
            case SINGLE:
                capacityList.add("SINGLE");
            case DOUBLE:
                capacityList.add("DOUBLE");
            case TRIPLE:
                capacityList.add("TRIPLE");
            case QUAD:
                capacityList.add("QUAD");
        }

        ArrayList<String> classOrder = new ArrayList<>(Arrays.asList("ECONOMY", "STANDARD", "FAMILY", "LUX"));
        switch (request.getClassID()) {
            case STANDARD:
                classOrder.add(0, classOrder.get(1));
                classOrder.remove(2);
                break;
            case FAMILY:
                classOrder.add(0, classOrder.get(2));
                classOrder.remove(3);
                break;
            case LUX:
                classOrder.add(0, classOrder.get(3));
                classOrder.remove(4);
                break;
        }

        ArrayList<String> capacityOrder = new ArrayList<>(Arrays.asList("SINGLE", "DOUBLE", "TRIPLE", "QUAD"));

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("checkin", request.getCheckIn());
        parameters.addValue("checkout", request.getCheckOut());
        parameters.addValue("capacityList", capacityList);

        parameters.addValue("class1", classOrder.get(0));
        parameters.addValue("class2", classOrder.get(1));
        parameters.addValue("class3", classOrder.get(2));
        parameters.addValue("class4", classOrder.get(3));

        parameters.addValue("cap1", capacityOrder.get(0));
        parameters.addValue("cap2", capacityOrder.get(1));
        parameters.addValue("cap3", capacityOrder.get(2));
        parameters.addValue("cap4", capacityOrder.get(3));


        return tempTemplate.query(GET_ROOMS_AVAILABLE_IN_PERIOD_AND_CAPACITY, parameters, new RoomRowMapper());
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

    class RequestRowMapper implements RowMapper<Request> {
        @Override
        public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
            Request request = new Request();
            request.setRequestID(rs.getLong("requestid"));
            request.setUserID(rs.getLong("userid"));
            request.setCapacity(Capacity.valueOf(rs.getString("capacity")));
            request.setClassID(ClassID.valueOf(rs.getString("classid")));
            request.setCheckIn(rs.getTimestamp("checkin"));
            request.setCheckOut(rs.getTimestamp("checkout"));
            request.setPaymentStatus(PaymentStatus.valueOf(rs.getString("paymentstatus")));
            return request;
        }
    }

}