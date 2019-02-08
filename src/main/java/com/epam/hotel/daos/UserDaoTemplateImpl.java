package com.epam.hotel.daos;

import com.epam.hotel.enums.Permission;
import com.epam.hotel.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoTemplateImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_QUERY = "INSERT INTO hotel.users (login, password, permission,firstname, lastname) values (?, ?, CAST(? as hotel.permission), ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM hotel.users WHERE userid = ?";
    private static final String UPDATE_QUERY = "UPDATE hotel.users SET login = ?, password = ?, permission = CAST(? as hotel.permission),firstname=?,lastname=? WHERE userid = ?";
    private static final String SELECT_ONE_QUERY = "SELECT userid, login, password, permission,firstname, lastname FROM hotel.users WHERE userid = ?";
    private static final String SELECT_ALL_QUERY = "SELECT userid, login, password, permission,firstname, lastname FROM hotel.users";

    public UserDaoTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User entity) {
        jdbcTemplate.update(INSERT_QUERY, entity.getLogin(), String.valueOf(entity.getPassword()), entity.getPermission().toString(), entity.getFirstName(), entity.getLastName());
        return entity;
    }

    @Override
    public boolean delete(long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
        return true;
    }

    @Override
    public boolean update(User entity) {
        jdbcTemplate.update(UPDATE_QUERY, entity.getLogin(), String.valueOf(entity.getPassword()), entity.getPermission().toString(), entity.getFirstName(), entity.getLastName(), entity.getId());
        return true;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, new UserRowMapper());
    }

    @Override
    public User getById(long id) {
        return jdbcTemplate.queryForObject(SELECT_ONE_QUERY, new Object[]{id}, new UserRowMapper());
    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("userid"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password").toCharArray());
            user.setPermission(Permission.valueOf(rs.getString("permission")));
            user.setFirstName(rs.getString("firstname"));
            user.setLastName(rs.getString("lastname"));
            return user;
        }
    }
}