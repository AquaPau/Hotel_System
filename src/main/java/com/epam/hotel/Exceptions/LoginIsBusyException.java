package com.epam.hotel.Exceptions;

public class LoginIsBusyException extends RuntimeException{

    public LoginIsBusyException(String message) {
        super(message);
    }

    public LoginIsBusyException() {
    }
}
