package com.epam.hotel.model;

import com.epam.hotel.model.enums.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class Room implements Comparable<Room> {

    private long roomID;
    private int roomNumber;
    private ClassID classID;
    private Capacity capacity;
    private BigDecimal price;

    public Room() {
    }

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

    @Override
    public int compareTo(@NotNull Room room) {
        if (this.roomID == room.getRoomID()) return 0;
        else if (this.roomID < room.getRoomID()) return -1;
        return 1;
    }
}

