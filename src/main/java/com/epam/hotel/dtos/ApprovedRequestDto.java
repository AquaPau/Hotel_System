package com.epam.hotel.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApprovedRequestDto {

    private long requestID;
    private String capacity;
    private String classID;
    private String paymentStatus;
    private String checkIn;
    private String checkOut;
    private int roomNumber;
    private long userID;
    private String firstName;
    private String lastName;

}
