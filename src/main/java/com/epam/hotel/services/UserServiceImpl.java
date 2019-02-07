package com.epam.hotel.services;

import com.epam.hotel.daos.UserDao;
import com.epam.hotel.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
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
        return userDao.getAll();
    }

    @Override
    public User getById(int id) {
        return null;
    }
}
