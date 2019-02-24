package com.epam.hotel.model;

import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    private Capacity capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "class")
    private ClassID classID;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentstatus")
    private PaymentStatus paymentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "checkin")
    private Date checkIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "checkout")
    private Date checkOut;

}
