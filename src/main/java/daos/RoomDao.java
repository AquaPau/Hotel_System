package daos;

import model.Room;
import org.springframework.jdbc.core.JdbcTemplate;

public class RoomDao {

    JdbcTemplate jdbctemplate;

    public RoomDao(JdbcTemplate template) {
        this.jdbctemplate = template;
    }

    public void addRoom(Room room) {
        jdbctemplate.execute("INSERT INTO hotel.rooms (logihotel.Users (roomnumber, classid, capacity, price) , password, firstName, lastName) values ('user1','1234','Petr','Ivanov');n, password, firstName, lastName) values ('user1','1234','Petr','Ivanov');");
        //INSERT INTO
    }

}
