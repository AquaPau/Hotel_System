package com.epam.hotel.model.converters;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class RequestConverter {
    private long requestID;
    private long userID;
    private String capacity;
    private String classID;
    private String paymentStatus;
    private String checkIn;
    private String checkOut;

    public static RequestConverter getRequestConverter(Request request) {
        requestValidation(request);
        RequestConverter converter = new RequestConverter();
        converter.requestID = request.getRequestID();
        converter.userID = request.getUserID();
        String capacity = request.getCapacity().name().substring(0, 1) + request.getCapacity().name().substring(1).toLowerCase();
        String classId = request.getClassID().name().substring(0, 1) + request.getClassID().name().substring(1).toLowerCase();
        String paymentStatus = request.getPaymentStatus().name().substring(0, 1) + request.getPaymentStatus().name().substring(1).toLowerCase();
        converter.capacity = capacity;
        converter.classID = classId;
        converter.paymentStatus = paymentStatus;
        converter.checkIn = convertDateToString(request.getCheckIn());
        converter.checkOut = convertDateToString(request.getCheckOut());
        return converter;
    }

    public Request getRequest() {
        Request request = new Request();
        request.setRequestID(this.getRequestID());
        request.setUserID(this.getUserID());
        request.setCapacity(Capacity.valueOf(this.capacity.toUpperCase()));
        request.setClassID(ClassID.valueOf(this.classID.toUpperCase()));
        request.setPaymentStatus(PaymentStatus.valueOf(this.paymentStatus.toUpperCase()));
        request.setCheckIn(convertStringToDate(this.checkIn));
        request.setCheckOut(convertStringToDate(this.checkOut));
        return request;
    }

    private static String convertDateToString(Timestamp timestamp) {
        int year = timestamp.toLocalDateTime().getYear();
        int month = timestamp.toLocalDateTime().getMonthValue();
        int day = timestamp.toLocalDateTime().getDayOfMonth();
        return String.format("%02d/%02d/%d", month, day, year);
    }

    private static Timestamp convertStringToDate(String date) {
        String[] split = date.split("/");
        int month = Integer.parseInt(split[0]);
        int day = Integer.parseInt(split[1]);
        int year = Integer.parseInt(split[2]);
        return Timestamp.valueOf(LocalDateTime.of(year, month, day, 0, 0));
    }

    public static void main(String[] args) {
        Request request = new Request();
        RequestConverter requestConverter = RequestConverter.getRequestConverter(request);
        System.out.println(requestConverter);
        System.out.println(requestConverter.getRequest());
    }

    private static void requestValidation(Request request) {
        if (request.getCheckIn() == null) {
            LocalDateTime ldt = LocalDateTime.now().plusDays(1);
            Timestamp timestamp = Timestamp.valueOf(ldt);
            request.setCheckIn(timestamp);
        }
        if (request.getCheckOut() == null) {
            LocalDateTime ldt = LocalDateTime.now().plusWeeks(1).plusDays(1);
            Timestamp timestamp = Timestamp.valueOf(ldt);
            request.setCheckOut(timestamp);
        }
        if (request.getCapacity() == null) {
            request.setCapacity(Capacity.SINGLE);
        }
        if (request.getClassID() == null) {
            request.setClassID(ClassID.STANDARD);
        }
        if (request.getPaymentStatus() == null) {
            request.setPaymentStatus(PaymentStatus.NOBILL);
        }
    }


}
