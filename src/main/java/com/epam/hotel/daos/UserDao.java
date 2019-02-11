package com.epam.hotel.daos;

import com.epam.hotel.model.User;

public interface UserDao extends Dao<User> {
    User getByLogin(String login);
}
