package com.epam.hotel.model;

import com.epam.hotel.enums.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Room {

    private long roomID;
    private int roomNumber;
    private ClassID classID;
    private Capacity capacity;
    private BigDecimal price;

}