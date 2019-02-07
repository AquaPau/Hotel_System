package com.epam.hotel.model;

import com.epam.hotel.enums.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Request {

    private long requestID;
    private long userID;
    private Capacity capacity;
    private ClassID classID;
    private PaymentStatus paymentStatus;
    private ZonedDateTime checkIn;
    private ZonedDateTime checkOut;

}