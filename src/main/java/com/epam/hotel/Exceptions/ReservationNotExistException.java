package com.epam.hotel.Exceptions;

public class ReservationNotExistException extends RuntimeException {
    public ReservationNotExistException(){

    }
    public ReservationNotExistException(String message){
        super(message);
    }
}
