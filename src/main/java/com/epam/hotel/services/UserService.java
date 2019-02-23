package com.epam.hotel.services;

import com.epam.hotel.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getUsers();

    User findById(Long id);

    User saveUser(User user);

    void deleteById(Long id);

}
