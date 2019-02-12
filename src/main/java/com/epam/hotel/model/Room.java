package com.epam.hotel.model;

import com.epam.hotel.model.enums.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Room {

    private long roomID;
    private int roomNumber;
    private ClassID classID;
    private Capacity capacity;
    private BigDecimal price;

    public Room() {};
    public Room(int num, ClassID classID, Capacity cap, BigDecimal price) {
        this.roomNumber = num;
        this.classID = classID;
        this.capacity = cap;
        this.price = price;
    }
    public Room(long id, int num, ClassID classID, Capacity cap, BigDecimal price) {
        this.roomID = id;
        this.roomNumber = num;
        this.classID = classID;
        this.capacity = cap;
        this.price = price;
    }

}