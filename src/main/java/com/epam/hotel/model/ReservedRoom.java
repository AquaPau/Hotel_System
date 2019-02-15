package com.epam.hotel.model;

import lombok.Data;

@Data
public class ReservedRoom {

    private long reservedRoomID;
    private long requestID;
    private int roomnumber;

}
