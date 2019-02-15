package com.epam.hotel.daos;

import com.epam.hotel.model.ReservedRoom;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ReservedRoomDaoTemplateImpl implements ReservedRoomDao {
    private final JdbcTemplate jdbcTemplate;
    private final String CREATE_RESERVATION = "INSERT INTO hotel.reservedrooms (roomnumber, requestid) VALUES " +
            "(?, ?)";
    private final String UPDATE_RESERVATION = "UPDATE hotel.reservedrooms SET  roomnumber = ?, requestid = ?" +
            "WHERE reservedroomid = ?";
    private final String DELETE_RESERVATION = "DELETE FROM hotel.reservedrooms WHERE reservedroomid = ?";
    private final String GET_RESERVATION_BY_ID = "SELECT * FROM hotel.reservedrooms WHERE reservedroomid = ?";
    private final String GET_ALL_RESERVATIONS = "SELECT * FROM hotel.reservedrooms";

    public ReservedRoomDaoTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservedRoom create(ReservedRoom reservedRoom) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION, new String[]{"reservedroomid"});
            preparedStatement.setInt(1, reservedRoom.getRoomnumber());
            preparedStatement.setLong(2, reservedRoom.getRequestID());
            return preparedStatement;
        }, keyHolder);
        reservedRoom.setReservedRoomID(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return reservedRoom;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(DELETE_RESERVATION, id) > 0;

    }

    @Override
    public boolean update(ReservedRoom reservedRoom) {
        return jdbcTemplate.update(UPDATE_RESERVATION, reservedRoom.getRoomnumber(), reservedRoom.getRequestID(), reservedRoom.getReservedRoomID()) > 0;
    }

    @Override
    public List<ReservedRoom> getAll() {
        return jdbcTemplate.query(GET_ALL_RESERVATIONS, new ReservedRoomRowMapper());
    }

    @Override
    public ReservedRoom getById(long id) {
        return jdbcTemplate.queryForObject(GET_RESERVATION_BY_ID, new Object[]{id},
                new ReservedRoomRowMapper());
    }

    class ReservedRoomRowMapper implements RowMapper<ReservedRoom> {
        @Override
        public ReservedRoom mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReservedRoom reservedRoom = new ReservedRoom();
            reservedRoom.setReservedRoomID(rs.getLong("reservedroomid"));
            reservedRoom.setRoomnumber(rs.getInt("roomnumber"));
            reservedRoom.setRequestID(rs.getLong("requestid"));
            return reservedRoom;
        }
    }
}
