package model;

import enums.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

import java.time.LocalDate;
import java.util.UUID;

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