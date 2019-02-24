package com.epam.hotel.services;

import com.epam.hotel.domains.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    User save(User user);

    void deleteById(Long id);

    User findByLogin(String login);

}
