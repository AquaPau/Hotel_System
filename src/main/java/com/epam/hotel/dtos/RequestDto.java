package com.epam.hotel.dtos;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.utils.DateFormatter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDto {
    private long requestID;
    private long userID;
    private String capacity;
    private String classID;
    private String paymentStatus;
    private String checkIn;
    private String checkOut;

    public RequestDto(Request request) {
        this.requestID = request.getRequestID();
        this.userID = request.getUserID();
        String capacity = request.getCapacity().name().substring(0, 1) + request.getCapacity().name().substring(1).toLowerCase();
        String classId = request.getClassID().name().substring(0, 1) + request.getClassID().name().substring(1).toLowerCase();
        String paymentStatus = request.getPaymentStatus().name().substring(0, 1) + request.getPaymentStatus().name().substring(1).toLowerCase();
        this.capacity = capacity;
        this.classID = classId;
        this.paymentStatus = paymentStatus;
        this.checkIn = DateFormatter.convertDateToString(request.getCheckIn());
        this.checkOut = DateFormatter.convertDateToString(request.getCheckOut());
    }

    public Request getRequest() {
        Request request = new Request();
        request.setRequestID(this.getRequestID());
        request.setUserID(this.getUserID());
        request.setCapacity(Capacity.valueOf(this.capacity.toUpperCase()));
        request.setClassID(ClassID.valueOf(this.classID.toUpperCase()));
        request.setPaymentStatus(PaymentStatus.valueOf(this.paymentStatus.toUpperCase()));
        request.setCheckIn(DateFormatter.convertStringToDate(this.checkIn));
        request.setCheckOut(DateFormatter.convertStringToDate(this.checkOut));
        return request;
    }

}

