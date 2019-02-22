package com.epam.hotel.model;

import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import lombok.*;
import org.springframework.data.annotation.Persistent;

import javax.persistence.*;
import java.security.Timestamp;

@Builder
@Getter
@Setter
@Persistent

public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestid")
    private long requestID;
    @Column(name = "userid")
    private long userID;
    @Column(name = "capacity")
    private Capacity capacity;
    @Enumerated(EnumType.STRING)
    @Column(name = "classid")
    private ClassID classID;
    @Enumerated(EnumType.STRING)
    @Column(name = "paymentstatus")
    private PaymentStatus paymentStatus;
    @Column(name = "checkin")
    private Timestamp checkIn;
    @Column(name = "checkout")
    private Timestamp checkOut;

}

