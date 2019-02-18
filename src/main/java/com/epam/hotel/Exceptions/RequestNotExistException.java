package com.epam.hotel.Exceptions;

public class RequestNotExistException extends RuntimeException {
    public RequestNotExistException(){

    }
    public RequestNotExistException(String message){
        super(message);
    }

}
