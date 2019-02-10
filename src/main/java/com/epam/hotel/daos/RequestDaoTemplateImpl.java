package com.epam.hotel.daos;

import com.epam.hotel.enums.Capacity;
import com.epam.hotel.enums.ClassID;
import com.epam.hotel.enums.PaymentStatus;
import com.epam.hotel.model.Request;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class RequestDaoTemplateImpl implements RequestDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_REQUEST_BY_ID = "SELECT * FROM hotel.requests WHERE requestid = ?";
    private static final String GET_ALL_REQUESTS = "SELECT * FROM hotel.requests";
    private static final String CREATE_NEW_REQUEST = "INSERT INTO hotel.requests (userid, capacity, classid, " +
            "checkin, checkout, paymentstatus) VALUES (?, ?::hotel.capacity, ?::hotel.classid, ?, ?, ?::hotel.paymentstatus)";
    private static final String UPDATE_REQUEST = "UPDATE hotel.requests SET userid = ?, " +
            "capacity = ?::hotel.capacity, classid = ?::hotel.classid, " +
            "checkin = ?, checkout = ?, paymentstatus = ?::hotel.paymentstatus WHERE requestid = ?";
    private static final String DELETE_REQUEST = "DELETE FROM hotel.requests WHERE requestid = ?";
    private static final String GET_REQUESTS_BY_USERID = "SELECT * FROM hotel.requests WHERE userid = ?";
    private static final String GET_REQUESTS_BY_PAYMENTSTATUS = "SELECT * FROM hotel.requests WHERE " +
            "paymentstatus = ?::hotel.paymentstatus";


    public RequestDaoTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Request> getUserRequests(long id) {
        return jdbcTemplate.query(GET_REQUESTS_BY_USERID, new RequestRowMapper());
    }

    @Override
    public List<Request> getPaymentStatus(PaymentStatus paymentStatus) {
        return jdbcTemplate.query(GET_REQUESTS_BY_PAYMENTSTATUS, new RequestRowMapper());
    }

    @Override
    public Request create(Request request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_REQUEST, new String[]{"requestid"});
            preparedStatement.setLong(1,request.getUserID());
            preparedStatement.setString(2, request.getCapacity().toString());
            preparedStatement.setString(3, request.getClassID().toString());
            preparedStatement.setTimestamp(4, request.getCheckIn());
            preparedStatement.setTimestamp(5, request.getCheckOut());
            preparedStatement.setString(6, request.getPaymentStatus().toString());
            return preparedStatement;
        }, keyHolder);
        request.setRequestID(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return request;

    }

    @Override
    public boolean delete(long id) {
        boolean isDeleted = jdbcTemplate.update(DELETE_REQUEST, id) > 0;
        return isDeleted;
    }

    @Override
    public boolean update(Request request) {
        boolean isUpdated = jdbcTemplate.update(UPDATE_REQUEST, request.getUserID(), request.getCapacity().toString(),
                    request.getClassID().toString(), request.getCheckIn(), request.getCheckOut(),
                    request.getPaymentStatus().toString()) > 0;
        return isUpdated;
    }

    @Override
    public List<Request> getAll() {
        return jdbcTemplate.query(GET_ALL_REQUESTS, new RequestRowMapper());
    }

    @Override
    public Request getById(long id) {
        return jdbcTemplate.queryForObject(GET_REQUEST_BY_ID, new Object[]{id}, new RequestRowMapper());
    }

    class RequestRowMapper implements RowMapper<Request> {
        @Override
        public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
            Request request = new Request();
            request.setRequestID(rs.getLong("requestid"));
            request.setUserID(rs.getLong("userid"));
            request.setClassID(ClassID.valueOf(rs.getString("classid")));
            request.setCapacity(Capacity.valueOf(rs.getString("capacity")));
            request.setCheckIn(rs.getTimestamp("checkin"));
            request.setCheckOut(rs.getTimestamp("checkout"));
            request.setPaymentStatus(PaymentStatus.valueOf(rs.getString("paymentstatus")));
            return request;
        }
    }
}
