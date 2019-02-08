package com.epam.hotel.daos;

import com.epam.hotel.model.Room;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RoomDaoJDBCImpl implements RoomDao {

    private static final String SQL_INSERT_ROOM =
            "INSERT INTO hotel.rooms (roomnumber, classid, capacity, price) VALUES (?, CAST(? AS hotel.classid), CAST(? AS hotel.capacity), ?)";
    private static final String SQL_UPDATE_ROOM =
            "UPDATE hotel.rooms SET roomnumber = ?, classid = CAST(? AS hotel.classid)," +
                    " capacity = CAST(? AS hotel.capacity), price = ?";

    @Override
    public Room create(Room room) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = null;
            stmt = conn.prepareStatement(SQL_INSERT_ROOM);
            stmt.setString(1, room.getRoomNumber()+"");
            stmt.setString(2, room.getClassID().name());
            stmt.setString(3, room.getCapacity().name());
            stmt.setString(4, room.getPrice().toString());
            stmt.execute();
        } catch (SQLException e) { // Обработать исключение
        } finally {
            try {
                if (stmt != null) { // Освободить ресурсы
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
            }
        }
        return room;
    }

    @Override
    public boolean delete(long id) {
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
    public Room getById(long id) {
        return null;
    }

    @Override
    public Room getByRoomNumber(int roomNumber) {
        return null;
    }
}
