package com.epam.hotel.exceptions;

public class RoomNumberAlreadyExistsException extends RuntimeException {
    public RoomNumberAlreadyExistsException(){}
    public RoomNumberAlreadyExistsException(String message){
        super(message);
    }
}
