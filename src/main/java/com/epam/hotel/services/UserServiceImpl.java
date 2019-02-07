package com.epam.hotel.services;

import com.epam.hotel.daos.UserDao;
import com.epam.hotel.enums.Permission;
import com.epam.hotel.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User create(User entity) {
        userValidation(entity);
        return userDao.create(entity);
    }

    @Override
    public boolean delete(long id) {
        return userDao.delete(id);
    }

    @Override
    public boolean update(User entity) {
        userValidation(entity);
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

    private void userValidation(User user) {
        if (user.getLogin() == null || user.getFirstName() == null || user.getLastName() == null) {
            throw new IllegalArgumentException("User is not valid: fill the required fields");
        }
        if (user.getPermission() == null) {
            user.setPermission(Permission.USER);
        }
    }
}
