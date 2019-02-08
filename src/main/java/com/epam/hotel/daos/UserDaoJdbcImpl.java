package com.epam.hotel.daos;

import com.epam.hotel.enums.Permission;
import com.epam.hotel.model.User;
import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDaoJdbcImpl implements UserDao {

    private String url;
    private String username;
    private String password;

    private static final String INSERT_QUERY = "INSERT INTO hotel.users (login, password, permission,firstname, lastname) values (?, ?, CAST(? as hotel.permission), ?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM hotel.users WHERE userid = ?";
    private static final String UPDATE_QUERY = "UPDATE hotel.users SET login = ?, password = ?, permission = CAST(? as hotel.permission),firstname=?,lastname=? WHERE userid = ?";
    private static final String SELECT_ONE_QUERY = "SELECT * FROM hotel.users WHERE userid = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM hotel.users";

    @Override
    public User create(User entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = initStatement(entity, connection, INSERT_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(User entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = initStatement(entity, connection, UPDATE_QUERY);
            statement.setLong(6, entity.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUser(resultSet);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public User getById(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ONE_QUERY);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return getUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PreparedStatement initStatement(User entity, Connection connection, String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entity.getLogin());
        statement.setString(2, String.valueOf(entity.getPassword()));
        statement.setString(3, entity.getPermission().toString());
        statement.setString(4, entity.getFirstName());
        statement.setString(5, entity.getLastName());
        return statement;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(1));
        user.setLogin(resultSet.getString(2));
        user.setPassword(resultSet.getString(3).toCharArray());
        user.setPermission(Permission.valueOf(resultSet.getString(4)));
        user.setFirstName(resultSet.getString(5));
        user.setLastName(resultSet.getString(6));
        return user;
    }

}
