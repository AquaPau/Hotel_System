package model;

import enums.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Request {

    private UUID requestID;
    private UUID userID;
    private Capacity capacity;
    private ClassID classID;
    private PaymentStatus paymentStatus;
    private LocalDate checkIn;
    private LocalDate checkOut;

}