package com.epam.hotel.daos;

import com.epam.hotel.enums.Permission;
import com.epam.hotel.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoTemplateImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDaoTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getUserCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hotel.Users", Integer.class);
    }


    @Override
    public User create(User entity) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public List<User> getAll() {
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

    @Override
    public User getById(int id) {
        return null;
    }
}