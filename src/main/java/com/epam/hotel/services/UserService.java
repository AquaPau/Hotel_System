package com.epam.hotel.services;

import com.epam.hotel.domains.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User save(User user);

    User update(User updatedUser, String currentPassword);

    void deleteById(Long id);

    User findByLogin(String login);

    Page<User> getAllUsersPaged(int page, int size);

    void changeUserBlockForId(Long id);

}
