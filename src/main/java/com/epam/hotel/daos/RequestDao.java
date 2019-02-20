package com.epam.hotel.daos;

import com.epam.hotel.dtos.ApprovedRequestDto;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.model.Request;
import java.util.List;


public interface RequestDao extends Dao <Request>{

    List<Request> getUserRequests(long id);
    List<Request> getPaymentStatus(PaymentStatus paymentStatus);
    boolean updatePaymentStatus(long id, PaymentStatus status);
    public List<ApprovedRequestDto> getAllApprovedRequests();
    public List<Request> getRequestsByPage(int page, int limit);

}
