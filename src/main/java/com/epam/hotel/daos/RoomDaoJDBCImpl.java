package com.epam.hotel.daos;

import com.epam.hotel.enums.Capacity;
import com.epam.hotel.enums.ClassID;
import com.epam.hotel.model.Room;
import lombok.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class RoomDaoJDBCImpl implements RoomDao {

    private String url;
    private String username;
    private String password;

    private static final String SQL_INSERT_ROOM =
            "INSERT INTO hotel.rooms (roomnumber, classid, capacity, price) VALUES (?, CAST(? AS hotel.classid), CAST(? AS hotel.capacity), ?)";
    private static final String SQL_UPDATE_ROOM =
            "UPDATE hotel.rooms SET roomnumber = ?, classid = CAST(? AS hotel.classid)," +
                    " capacity = CAST(? AS hotel.capacity), price = ? WHERE roomid = ?";
    private static final String SQL_SELECT_ONE_BY_ID = "SELECT * FROM hotel.rooms WHERE roomid = ?";
    private static final String SQL_SELECT_ONE_BY_NUMBER = "SELECT * FROM hotel.rooms WHERE roomnumber = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM hotel.rooms";
    private static final String SQL_DELETE_ROOM = "DELETE FROM hotel.rooms WHERE roomid = ?";

    private PreparedStatement initStatement(Room room, Connection connection, String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, room.getRoomNumber()+"");
        statement.setString(2, room.getClassID().name());
        statement.setString(3, room.getCapacity().name());
        statement.setString(4, room.getPrice().toString());
        return statement;
    }

    private Room getRoom(ResultSet resultSet) throws SQLException {
        Room room = new Room();
        room.setRoomID(resultSet.getLong(1));
        room.setRoomNumber(resultSet.getInt(2));
        room.setClassID(ClassID.valueOf(resultSet.getString(3)));
        room.setCapacity(Capacity.valueOf(resultSet.getString(4)));
        room.setPrice(resultSet.getBigDecimal(5));
        return room;
    }

    @Override
    public Room create(Room room) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = initStatement(room, connection, SQL_INSERT_ROOM);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ROOM);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Room room) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = initStatement(room, connection, SQL_UPDATE_ROOM);
            statement.setLong(5, room.getRoomID());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Room> getAll() {
        List<Room> roomList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Room room = getRoom(resultSet);
                roomList.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    @Override
    public Room getById(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ONE_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return getRoom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Room getByRoomNumber(int roomNumber) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ONE_BY_NUMBER);
            statement.setLong(1, roomNumber);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return getRoom(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}