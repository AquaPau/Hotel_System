package com.epam.hotel.daos;

import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.model.Request;
import java.util.List;


public interface RequestDao extends Dao <Request>{
    List<Request> getUserRequests(long id);
    List<Request> getPaymentStatus(PaymentStatus paymentStatus);

}
