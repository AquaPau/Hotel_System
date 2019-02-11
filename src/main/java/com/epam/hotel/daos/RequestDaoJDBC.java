package com.epam.hotel.daos;

import com.epam.hotel.enums.Capacity;
import com.epam.hotel.enums.ClassID;
import com.epam.hotel.enums.PaymentStatus;
import com.epam.hotel.model.Request;
import lombok.Data;


import java.sql.*;
;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RequestDaoJDBC implements RequestDao {
    private String url;
    private String username;
    private String password;

    private final String GET_REQUEST_BY_ID = "SELECT * FROM hotel.requests WHERE requestid = ?";
    private final String GET_ALL_REQUESTS = "SELECT * FROM hotel.requests";
    private final String CREATE_NEW_REQUEST = "INSERT INTO hotel.requests (requestid, userid, capacity, classid, " +
            "checkin, checkout, paymentstatus) VALUES (?, ?, ?::hotel.capacity, ?::hotel.classid, ?, ?, ?::hotel.paymentstatus)";
    private final String UPDATE_REQUEST = "UPDATE hotel.requests SET userid = ?, " +
            "capacity = ?::hotel.capacity, classid = ?::hotel.classid, " +
            "checkin = ?, checkout = ?, paymentstatus = ?::hotel.paymentstatus WHERE requestid = ?";
    private final String DELETE_REQUEST = "DELETE FROM hotel.requests WHERE requestid = ?";
    private final String GET_REQUESTS_BY_USERID = "SELECT * FROM hotel.requests WHERE userid = ?";
    private final String GET_REQUESTS_BY_PAYMENTSTATUS = "SELECT * FROM hotel.requests WHERE " +
            "paymentstatus = ?::hotel.paymentstatus";

    private void extractRequestBody(ResultSet rs, Request request) throws SQLException {
        request.setRequestID(rs.getLong("requestid"));
        request.setUserID(rs.getLong("userid"));
        request.setCapacity(Capacity.valueOf(rs.getString("capacity")));
        request.setClassID(ClassID.valueOf(rs.getString("classid")));
        request.setCheckIn(rs.getTimestamp("checkin"));
        request.setCheckOut(rs.getTimestamp("checkout"));
        request.setPaymentStatus(PaymentStatus.valueOf(rs.getString("paymentstatus")));
    }

    private void initRequestBody(PreparedStatement preparedStatement, Request request)
            throws SQLException {
        preparedStatement.setLong(1, request.getRequestID());
        preparedStatement.setLong(2, request.getUserID());
        preparedStatement.setString(3, request.getCapacity().toString());
        preparedStatement.setString(4, request.getClassID().toString());
        preparedStatement.setTimestamp(5, request.getCheckIn());
        preparedStatement.setTimestamp(6, request.getCheckOut());
        preparedStatement.setString(7, request.getPaymentStatus().toString());

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
        return null;
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
        return null;
    }

    @Override
    public Request create(Request entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_REQUEST);
            if (getById(entity.getRequestID()) != null) throw new SQLException("Request already exists");
            initRequestBody(preparedStatement, entity);
            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
        }
        return null;
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
    public boolean update(Request entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_REQUEST);
            preparedStatement.setLong(7, entity.getRequestID());
            preparedStatement.setLong(1, entity.getUserID());
            preparedStatement.setString(2, entity.getCapacity().toString());
            preparedStatement.setString(3, entity.getClassID().toString());
            preparedStatement.setTimestamp(4, entity.getCheckIn());
            preparedStatement.setTimestamp(5, entity.getCheckOut());
            preparedStatement.setString(6, entity.getPaymentStatus().toString());
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
        return null;
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
        return null;
    }


}
