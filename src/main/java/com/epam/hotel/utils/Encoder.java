package com.epam.hotel.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encoder {

    private Encoder() {
    }

    private final static PasswordEncoder encoder = new BCryptPasswordEncoder(11);

    public static String encode(String s) {
        return encoder.encode(s);
    }

    public static boolean matches(String newPassword, String oldPassword){
        return encoder.matches(newPassword,oldPassword);
    }
}
