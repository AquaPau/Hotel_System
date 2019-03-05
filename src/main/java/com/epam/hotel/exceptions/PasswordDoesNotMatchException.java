package com.epam.hotel.exceptions;

public class PasswordDoesNotMatchException extends RuntimeException {

    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}
