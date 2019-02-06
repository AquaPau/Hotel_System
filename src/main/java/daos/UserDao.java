package daos;

import enums.Permission;
import model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDao implements Dao {
    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getUserCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hotel.Users", Integer.class);
    }

    public List<User> getUserList() {
        return jdbcTemplate.query("SELECT * FROM hotel.Users", (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong(1));
            user.setLogin(rs.getString(2));
            user.setPermission(Permission.valueOf(rs.getString(4)));
            user.setFirstName(rs.getString(5));
            user.setLastName(rs.getString(6));
            return user;
        });
    }
}