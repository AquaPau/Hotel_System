package com.epam.hotel.services;

import com.epam.hotel.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User findById(Long id);

    User save(User user);

    void deleteById(Long id);

}
