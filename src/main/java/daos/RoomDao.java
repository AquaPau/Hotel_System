package daos;

import enums.ClassID;
import model.Room;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;
import enums.*;

public class RoomDao implements Dao {

    JdbcTemplate jdbcTemplate;

    public RoomDao(JdbcTemplate jdbcTemplate) {
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

}