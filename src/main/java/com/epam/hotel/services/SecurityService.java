package com.epam.hotel.services;

public interface SecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
