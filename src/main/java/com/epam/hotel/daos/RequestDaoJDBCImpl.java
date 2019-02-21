package com.epam.hotel.daos;

import com.epam.hotel.dtos.ApprovedRequestDto;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.model.Request;
import lombok.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class RequestDaoJDBCImpl implements RequestDao {

    private String url;
    private String username;
    private String password;
    private final String GET_REQUEST_BY_ID = "SELECT * FROM hotel.requests WHERE requestid = ?";
    private final String GET_ALL_REQUESTS = "SELECT * FROM hotel.requests";
    private final String CREATE_NEW_REQUEST = "INSERT INTO hotel.requests (userid, capacity, classid, " +
            "checkin, checkout, paymentstatus) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_REQUEST = "UPDATE hotel.requests SET userid = ?, " +
            "capacity = ?, classid = ?, " +
            "checkin = ?, checkout = ?, paymentstatus = ? WHERE requestid = ?";
    private final String DELETE_REQUEST = "DELETE FROM hotel.requests WHERE requestid = ?";
    private final String GET_REQUESTS_BY_USERID = "SELECT * FROM hotel.requests WHERE userid = ?";
    private final String GET_REQUESTS_BY_PAYMENTSTATUS = "SELECT * FROM hotel.requests WHERE " +
            "paymentstatus = ?";
    private final String SQL_GET_ALL_APPROVED_REQUESTS = "SELECT hotel.requests.requestid, hotel.requests.capacity, " +
            " hotel.requests.classid, hotel.requests.checkin, hotel.requests.checkout, hotel.requests.paymentstatus," +
            " hotel.rooms.roomnumber, hotel.requests.userid, hotel.users.firstname, hotel.users.lastname" +
            " FROM hotel.users RIGHT JOIN hotel.rooms RIGHT JOIN hotel.reservedrooms FULL JOIN hotel.requests" +
            " ON reservedrooms.requestid = requests.requestid ON rooms.roomid = reservedrooms.roomid" +
            " ON requests.userid = users.userid WHERE roomnumber NOTNULL ORDER BY requests.checkin";
    private final String SQL_GET_REQUESTS_PAGE = "SELECT * FROM hotel.requests ORDER BY requestid OFFSET ? LIMIT ?";

    private void extractRequestBody(ResultSet rs, Request request) throws SQLException {
        request.setRequestID(rs.getLong("requestid"));
        request.setUserID(rs.getLong("userid"));
        request.setCapacity(Capacity.valueOf(rs.getString("capacity")));
        request.setClassID(ClassID.valueOf(rs.getString("classid")));
        request.setCheckIn(rs.getTimestamp("checkin"));
        request.setCheckOut(rs.getTimestamp("checkout"));
        request.setPaymentStatus(PaymentStatus.valueOf(rs.getString("paymentstatus")));
    }

    private PreparedStatement initRequestBody(Connection connection, Request request, String query)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setLong(1, request.getUserID());
        preparedStatement.setString(2, request.getCapacity().toString());
        preparedStatement.setString(3, request.getClassID().toString());
        preparedStatement.setTimestamp(4, request.getCheckIn());
        preparedStatement.setTimestamp(5, request.getCheckOut());
        preparedStatement.setString(6, request.getPaymentStatus().toString());
        return preparedStatement;
    }

    @Override
    public List<Request> getUserRequests(long id) {
        List<Request> requestList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_REQUESTS_BY_USERID);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return getRequestsList(requestList, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<Request> getRequestsList(List<Request> requestList, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Request request = new Request();
            extractRequestBody(rs, request);
            requestList.add(request);
        }
        return requestList;
    }

    @Override
    public List<Request> getPaymentStatus(PaymentStatus paymentStatus) {
        List<Request> requestList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_REQUESTS_BY_PAYMENTSTATUS);
            preparedStatement.setString(1, paymentStatus.toString());
            ResultSet rs = preparedStatement.executeQuery();
            return getRequestsList(requestList, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean updatePaymentStatus(long id, PaymentStatus status) {
        return true;
    }

    @Override
    public List<ApprovedRequestDto> getAllApprovedRequests() {
        return null;
    }

    @Override
    public List<Request> getRequestsByPage(int page, int limit) {
        return null;
    }

    @Override
    public List<Request> getPagedProcessedRequestByUserId(long id, int offset, int limit) {
        return null;
    }

    @Override
    public List<Request> getPagedUnprocessedRequestByUserId(long id, int offset, int limit) {
        return null;
    }

    @Override
    public long getProcessedRequestByUserIdCount(long id) {
        return 0;
    }

    @Override
    public long getUnprocessedRequestByUserIdCount(long id) {
        return 0;
    }

    @Override
    public Request create(Request request) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = initRequestBody(connection, request, CREATE_NEW_REQUEST);
            int numberRowsUpdated = preparedStatement.executeUpdate();
            if (numberRowsUpdated == 0) throw new SQLException("Creating request failed, no rows affected.");
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    request.setRequestID(generatedKeys.getLong(1));
                } else throw new SQLException("Creating request failed, no ID obtained.");
            }
        } catch (SQLException e) {

        }
        return request;
    }


    @Override
    public boolean delete(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REQUEST);
            if (getById(id) == null) throw new SQLException("Request doesn't exist");
            preparedStatement.setLong(1, id);
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Request request) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = initRequestBody(connection, request, UPDATE_REQUEST);
            preparedStatement.setLong(7, request.getRequestID());
            return (preparedStatement.executeUpdate() > 0);
        } catch (SQLException e) {
        }
        return false;
    }

    @Override
    public List<Request> getAll() {
        List<Request> requestList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_REQUESTS);
            ResultSet rs = preparedStatement.executeQuery();
            return getRequestsList(requestList, rs);
        } catch (SQLException e) {
        }
        return new ArrayList<>();
    }


    @Override
    public Request getById(long id) {
        Request request = new Request();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_REQUEST_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            extractRequestBody(rs, request);
            return request;
        } catch (SQLException e) {
        }
        return new Request();
    }


}

