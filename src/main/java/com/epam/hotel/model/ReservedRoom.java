package com.epam.hotel.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ReservedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "room_id")
    private long roomId;

    @Column(name = "request_id")
    private long requestId;

}
