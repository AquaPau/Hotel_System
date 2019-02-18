package com.epam.hotel.daos;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReservedRoomDaoJdbcImpl implements ReservedRoomDao {

    private String url;
    private String username;
    private String password;

    private final String CREATE_RESERVATION = "INSERT INTO hotel.reservedrooms (roomnumber, requestid) VALUES " +
            "(?, ?)";
    private final String UPDATE_RESERVATION = "UPDATE hotel.reservedrooms SET  roomnumber = ?, requestid = ?" +
            "WHERE reservedroomid = ?";
    private final String DELETE_RESERVATION = "DELETE FROM hotel.reservedrooms WHERE reservedroomid = ?";
    private final String GET_RESERVATION_BY_ID = "SELECT * FROM hotel.reservedrooms WHERE reservedroomid = ?";
    private final String GET_ALL_RESERVATIONS = "SELECT * FROM hotel.reservedrooms";
    private final String SQL_ADD_TO_RESERVED_ROOMS = "INSERT INTO hotel.reservedrooms (roomnumber, requestid) VALUES ((SELECT hotel.rooms.roomnumber" +
            "  FROM hotel.rooms WHERE hotel.rooms.roomnumber = ?), (SELECT hotel.requests.requestid" +
            "  FROM hotel.requests WHERE hotel.requests.requestid = ?))";
    private final String SQL_GET_REQUESTS_BY_ROOM_NUMBER = "SELECT hotel.requests.requestid," +
            "  hotel.requests.userid, hotel.requests.capacity, hotel.requests.classid, hotel.requests.checkin," +
            "  hotel.requests.checkout, hotel.requests.paymentstatus FROM hotel.reservedrooms left join hotel.requests" +
            "  on reservedrooms.requestid = requests.requestid WHERE hotel.reservedrooms.roomnumber = ?";

    private PreparedStatement initReservedRoomBody(Connection connection, ReservedRoom reservedRoom, String query)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, reservedRoom.getRoomID());
        preparedStatement.setLong(2, reservedRoom.getRequestID());
        return preparedStatement;
    }

    private void extractReservedRoomBody(ResultSet rs, ReservedRoom reservedRoom) throws SQLException {
        reservedRoom.setReservedRoomID(rs.getLong("reservedroomid"));
        reservedRoom.setRoomID(rs.getInt("roomnumber"));
        reservedRoom.setRequestID(rs.getLong("requestid"));
    }

    private List<ReservedRoom> getReservedRoomList(List<ReservedRoom> reservedRoomList, ResultSet resultSet)
            throws SQLException {
        while (resultSet.next()) {
            ReservedRoom reservedRoom = new ReservedRoom();
            extractReservedRoomBody(resultSet, reservedRoom);
            reservedRoomList.add(reservedRoom);
        }
        return reservedRoomList;
    }

    @Override
    public ReservedRoom create(ReservedRoom reservedRoom) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = initReservedRoomBody(connection, reservedRoom, CREATE_RESERVATION);
            int numberRowsUpdated = preparedStatement.executeUpdate();
            if (numberRowsUpdated == 0) throw new SQLException("Creating reservation failed, no rows affected");
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) reservedRoom.setReservedRoomID(generatedKeys.getLong(1));
                else throw new SQLException("Creating reservation failed, no ID obtained");
            }
        } catch (SQLException e) {

        }
        return reservedRoom;
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION);
            if (getById(id) == null) throw new SQLException("Reservation doesn't exist");
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {

        }
        return false;
    }

    @Override
    public boolean update(ReservedRoom reservedRoom) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = initReservedRoomBody(connection, reservedRoom, UPDATE_RESERVATION);
            preparedStatement.setLong(3, reservedRoom.getReservedRoomID());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {

        }
        return false;
    }

    @Override
    public List<ReservedRoom> getAll() {
        List<ReservedRoom> reservedRoomList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_RESERVATIONS);
            ResultSet resultSet = preparedStatement.executeQuery();
            return getReservedRoomList(reservedRoomList, resultSet);
        } catch (SQLException e) {

        }
        return new ArrayList<>();
    }

    @Override
    public ReservedRoom getById(long id) {
        ReservedRoom reservedRoom = new ReservedRoom();
        try (Connection connection = DriverManager.getConnection(url, username, password)){
            PreparedStatement preparedStatement = connection.prepareStatement(GET_RESERVATION_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            extractReservedRoomBody(resultSet, reservedRoom);
            return reservedRoom;
        } catch (SQLException e){

        }
        return new ReservedRoom();
    }

}
