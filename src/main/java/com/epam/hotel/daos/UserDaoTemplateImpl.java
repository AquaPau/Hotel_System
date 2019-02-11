package com.epam.hotel.daos;

import com.epam.hotel.enums.Permission;
import com.epam.hotel.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Slf4j
public class UserDaoTemplateImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_QUERY = "INSERT INTO hotel.users (login, password, permission,firstname, lastname) values (?, ?, ?, ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM hotel.users WHERE userid = ?";
    private static final String UPDATE_QUERY = "UPDATE hotel.users SET login = ?, password = ?, permission = ?, firstname=?,lastname=? WHERE userid = ?";
    private static final String SELECT_BY_ID_QUERY = "SELECT userid, login, password, permission,firstname, lastname FROM hotel.users WHERE userid = ?";
    private static final String SELECT_BY_LOGIN_QUERY = "SELECT userid, login, password, permission,firstname, lastname FROM hotel.users WHERE login = ?";
    private static final String SELECT_ALL_QUERY = "SELECT userid, login, password, permission,firstname, lastname FROM hotel.users";

    public UserDaoTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getByLogin(String login) {
        return jdbcTemplate.queryForObject(SELECT_BY_LOGIN_QUERY, new Object[]{login}, new UserRowMapper());
    }

    @Override
    public User create(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(INSERT_QUERY, new String[]{"userid"});
                    statement.setString(1, entity.getLogin());
                    statement.setString(2, entity.getPassword());
                    statement.setString(3, entity.getPermission().toString());
                    statement.setString(4, entity.getFirstName());
                    statement.setString(5, entity.getLastName());
                    return statement;
                },
                keyHolder);
        entity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.info("Added user with id="+entity.getId());
        return entity;
    }

    @Override
    public boolean delete(long id) {
        log.info("Deleted user with id="+id);
        return jdbcTemplate.update(DELETE_QUERY, id) != 0;
    }

    @Override
    public boolean update(User entity) {
        log.info("Deleted user with id="+entity.getId());
        int rows = jdbcTemplate.update(UPDATE_QUERY, entity.getLogin(), String.valueOf(entity.getPassword()), entity.getPermission().toString(), entity.getFirstName(), entity.getLastName(), entity.getId());
        return rows != 0;
    }

    @Override
    public List<User> getAll() {
        log.info("Retrieved list of all users");
        return jdbcTemplate.query(SELECT_ALL_QUERY, new UserRowMapper());
    }

    @Override
    public User getById(long id) {
        log.info("Accessed user with id="+id);
        return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, new Object[]{id}, new UserRowMapper());
    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("userid"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setPermission(Permission.valueOf(rs.getString("permission")));
            user.setFirstName(rs.getString("firstname"));
            user.setLastName(rs.getString("lastname"));
            return user;
        }
    }
}
