package com.epam.hotel.services;

import com.epam.hotel.enums.PaymentStatus;
import com.epam.hotel.model.Request;

import java.util.List;

public interface RequestService extends Service<Request>{
    List<Request> getUserRequests(long id);
    List<Request> getPaymentStatus(PaymentStatus paymentStatus);
}
