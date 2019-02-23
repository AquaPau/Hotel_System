package com.epam.hotel.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "reservedrooms")
public class ReservedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservedroomid")
    private long reservedRoomID;

    @Column(name = "roomid")
    private long roomID;

    @Column(name = "requestid")
    private long requestID;

}
