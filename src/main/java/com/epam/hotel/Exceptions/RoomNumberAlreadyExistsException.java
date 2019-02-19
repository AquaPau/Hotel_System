package com.epam.hotel.Exceptions;

public class RoomNumberAlreadyExistsException extends RuntimeException {
    public RoomNumberAlreadyExistsException(){

    }
    public RoomNumberAlreadyExistsException(String message){
        super(message);
    }
}
