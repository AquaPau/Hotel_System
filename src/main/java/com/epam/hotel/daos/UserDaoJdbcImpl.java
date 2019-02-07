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

    @Override
    public User create(User entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String createSQL = "INSERT INTO hotel.users (login, password, permission,firstname, lastname) values (?, ?, CAST(? as hotel.permission), ?, ?)";
            PreparedStatement statement = connection.prepareStatement(createSQL);
            statement.setString(1, entity.getLogin());
            statement.setString(2, String.valueOf(entity.getPassword()));
            if (entity.getPermission() == null) {
                entity.setPermission(Permission.USER);
            }
            statement.setString(3, entity.getPermission().toString());
            statement.setString(4, entity.getFirstName());
            statement.setString(5, entity.getLastName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String deleteSQL = "DELETE FROM hotel.users WHERE userid = ?";
            PreparedStatement statement = connection.prepareStatement(deleteSQL);
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
            String updateSQL = "UPDATE hotel.users "
                    + "SET login = ?, password = ?, permission = CAST(? as hotel.permission),firstname=?,lastname=?"
                    + "WHERE userid = ?";
            PreparedStatement statement = connection.prepareStatement(updateSQL);
            statement.setString(1, entity.getLogin());
            statement.setString(2, String.valueOf(entity.getPassword()));
            if (entity.getPermission() == null) {
                entity.setPermission(Permission.USER);
            }
            statement.setString(3, entity.getPermission().toString());
            statement.setString(4, entity.getFirstName());
            statement.setString(5, entity.getLastName());
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
            String query = "SELECT * FROM hotel.users";
            PreparedStatement statement = connection.prepareStatement(query);
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

    @Override
    public User getById(long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM hotel.users WHERE userid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return getUser(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
