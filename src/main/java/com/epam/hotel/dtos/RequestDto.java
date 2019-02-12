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
        this.capacity = request.getCapacity().name();
        this.classID = request.getClassID().name();
        this.paymentStatus = request.getPaymentStatus().name();
        this.checkIn = DateFormatter.convertDateToString(request.getCheckIn());
        this.checkOut = DateFormatter.convertDateToString(request.getCheckOut());
    }

    public Request getRequest() {
        Request request = new Request();
        request.setRequestID(this.getRequestID());
        request.setUserID(this.getUserID());
        request.setCapacity(Capacity.valueOf(this.capacity));
        request.setClassID(ClassID.valueOf(this.classID));
        request.setPaymentStatus(PaymentStatus.valueOf(this.paymentStatus));
        request.setCheckIn(DateFormatter.convertStringToDate(this.checkIn));
        request.setCheckOut(DateFormatter.convertStringToDate(this.checkOut));
        return request;
    }

}

