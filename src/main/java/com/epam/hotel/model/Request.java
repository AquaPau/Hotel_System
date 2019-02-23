package com.epam.hotel.model;

import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Data
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestid")
    private long requestID;

    @Column(name = "userid")
    private long userID;

    @Enumerated(EnumType.STRING)
    private Capacity capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "classid")
    private ClassID classID;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentstatus")
    private PaymentStatus paymentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "checkin")
    private Calendar checkIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "checkout")
    private Calendar checkOut;

}
