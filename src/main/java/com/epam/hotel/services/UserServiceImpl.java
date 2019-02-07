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
        return userDao.create(entity);
    }

    @Override
    public boolean delete(long id) {
        return userDao.delete(id);
    }

    @Override
    public boolean update(User entity) {
        return userDao.update(entity);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getById(long id) {
        return userDao.getById(id);
    }
}
