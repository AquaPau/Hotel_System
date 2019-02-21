package com.epam.hotel.daos;

import com.epam.hotel.dtos.ApprovedRequestDto;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.model.Request;
import com.epam.hotel.utils.DateFormatter;
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
            "checkin, checkout, paymentstatus) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_REQUEST = "UPDATE hotel.requests SET userid = ?, " +
            "capacity = ?, classid = ?, " +
            "checkin = ?, checkout = ?, paymentstatus = ? WHERE requestid = ?";
    private static final String DELETE_REQUEST = "DELETE FROM hotel.requests WHERE requestid = ?";
    private static final String GET_REQUESTS_BY_USERID = "SELECT * FROM hotel.requests WHERE userid = %d";
    private static final String GET_REQUESTS_BY_PAYMENTSTATUS = "SELECT * FROM hotel.requests WHERE " +
            "paymentStatus = %s";
    private static final String UPDATE_BILLING_STATUS = "UPDATE hotel.requests SET paymentstatus = ? WHERE requestid = ?";
    private final String SQL_GET_ALL_APPROVED_REQUESTS = "SELECT hotel.requests.requestid, hotel.requests.capacity, " +
            " hotel.requests.classid, hotel.requests.checkin, hotel.requests.checkout, hotel.requests.paymentstatus," +
            " hotel.rooms.roomnumber, hotel.requests.userid, hotel.users.firstname, hotel.users.lastname" +
            " FROM hotel.users RIGHT JOIN hotel.rooms RIGHT JOIN hotel.reservedrooms FULL JOIN hotel.requests" +
            " ON reservedrooms.requestid = requests.requestid ON rooms.roomid = reservedrooms.roomid" +
            " ON requests.userid = users.userid WHERE roomnumber NOTNULL ORDER BY requests.checkin";
    private final String SQL_GET_UNAPPROVED_REQUESTS_PAGE = "SELECT * FROM hotel.requests " +
            "WHERE paymentstatus='NOBILL' ORDER BY requestid OFFSET ? LIMIT ?";
    private final String SQL_GET_APPROVED_REQUESTS_PAGE = "SELECT hotel.requests.requestid, hotel.requests.capacity, " +
            " hotel.requests.classid, hotel.requests.checkin, hotel.requests.checkout, hotel.requests.paymentstatus," +
            " hotel.rooms.roomnumber, hotel.requests.userid, hotel.users.firstname, hotel.users.lastname" +
            " FROM hotel.users RIGHT JOIN hotel.rooms RIGHT JOIN hotel.reservedrooms FULL JOIN hotel.requests" +
            " ON reservedrooms.requestid = requests.requestid ON rooms.roomid = reservedrooms.roomid" +
            " ON requests.userid = users.userid WHERE roomnumber NOTNULL ORDER BY requests.checkin OFFSET ? LIMIT ?";


    public RequestDaoTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Request> getUserRequests(long id) {
        String query = String.format(GET_REQUESTS_BY_USERID, id);
        return jdbcTemplate.query(query, new RequestRowMapper());
    }

    @Override
    public List<Request> getPaymentStatus(PaymentStatus paymentStatus) {
        String query = String.format(GET_REQUESTS_BY_PAYMENTSTATUS, paymentStatus.toString());
        return jdbcTemplate.query(query, new RequestRowMapper());
    }

    @Override
    public boolean updatePaymentStatus(long id, PaymentStatus status) {
        return jdbcTemplate.update(UPDATE_BILLING_STATUS, status.name(), id) > 0;
    }

    @Override
    public Request create(Request request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_REQUEST, new String[]{"requestid"});
            preparedStatement.setLong(1, request.getUserID());
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
        return jdbcTemplate.update(DELETE_REQUEST, id) > 0;
    }

    @Override
    public boolean update(Request request) {
        return jdbcTemplate.update(UPDATE_REQUEST, request.getUserID(), request.getCapacity().toString(),
                request.getClassID().toString(), request.getCheckIn(), request.getCheckOut(),
                request.getPaymentStatus().toString(), request.getRequestID()) > 0;
    }

    @Override
    public List<Request> getAll() {
        return jdbcTemplate.query(GET_ALL_REQUESTS, new RequestRowMapper());
    }

    @Override
    public Request getById(long id) {
        Request result = jdbcTemplate.queryForObject(GET_REQUEST_BY_ID, new Object[]{id}, new RequestRowMapper());
        if (result != null) { return result; }
        else return null;
    }

    @Override
    public List<ApprovedRequestDto> getAllApprovedRequests() {
        return jdbcTemplate.query(SQL_GET_ALL_APPROVED_REQUESTS, new ApprovedRequestRowMapper());
    }

    @Override
    public List<ApprovedRequestDto> getApprovedRequestsByPage(int page, int limit) {
        int offset = (page - 1)*limit;
        return jdbcTemplate.query(SQL_GET_APPROVED_REQUESTS_PAGE, new Object[]{offset, limit},new ApprovedRequestRowMapper());
    }

    @Override
    public List<Request> getRequestsByPage(int page, int limit) {
        int offset = (page-1)*limit;
        return jdbcTemplate.query(SQL_GET_UNAPPROVED_REQUESTS_PAGE, new Object[]{offset, limit},new RequestRowMapper());
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

    class ApprovedRequestRowMapper implements RowMapper<ApprovedRequestDto> {
        @Override
        public ApprovedRequestDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            ApprovedRequestDto approvedRequestDto = new ApprovedRequestDto();
            approvedRequestDto.setRequestID(rs.getLong("requestid"));
            approvedRequestDto.setRoomNumber(rs.getInt("roomnumber"));
            approvedRequestDto.setUserID(rs.getLong("userid"));
            approvedRequestDto.setFirstName(rs.getString("firstname"));
            approvedRequestDto.setLastName(rs.getString("lastname"));
            approvedRequestDto.setCapacity(rs.getString("capacity"));
            approvedRequestDto.setClassID(rs.getString("classid"));
            approvedRequestDto.setCheckIn(DateFormatter.convertDateToString(rs.getTimestamp("checkin")));
            approvedRequestDto.setCheckOut(DateFormatter.convertDateToString(rs.getTimestamp("checkout")));
            approvedRequestDto.setPaymentStatus(rs.getString("paymentstatus"));
            return approvedRequestDto;
        }
    }

}
