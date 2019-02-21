package com.epam.hotel.daos;

import com.epam.hotel.dtos.ApprovedRequestDto;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.model.Request;

import java.util.List;


public interface RequestDao extends Dao<Request> {

    List<Request> getUserRequests(long id);

    List<Request> getPaymentStatus(PaymentStatus paymentStatus);

    boolean updatePaymentStatus(long id, PaymentStatus status);

    public List<ApprovedRequestDto> getApprovedRequestsByPage(int page, int limit);

    List<ApprovedRequestDto> getAllApprovedRequests();

    List<Request> getRequestsByPage(int page, int limit);

    List<Request> getPagedProcessedRequestByUserId(long id, int offset, int limit);

    List<Request> getPagedUnprocessedRequestByUserId(long id, int offset, int limit);

    long getProcessedRequestByUserIdCount(long id);

    long getUnprocessedRequestByUserIdCount(long id);

}
