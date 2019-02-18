package com.epam.hotel.Exceptions;

public class RoomNotExistException extends RuntimeException {
    public RoomNotExistException() {

    }

    public RoomNotExistException(String message) {
        super(message);
    }

}
