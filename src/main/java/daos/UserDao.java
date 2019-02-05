package daos;

import model.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {
    JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getUserCount() {
        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hotel.Users", Integer.class);
        return count;
    }
}
