package com.epam.hotel.services;

import com.epam.hotel.dtos.ApprovedRequestDto;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.enums.PaymentStatus;

import java.util.List;

public interface RequestService extends Service<Request> {
    List<Request> getUserRequests(long id);

    List<Request> getPaymentStatus(PaymentStatus paymentStatus);

    RequestDto getRequestDto(Request request);

    Request getRequestFromDto(RequestDto requestDto);

    List<RequestDto> getUserRequestsDto(long id);

    List<RequestDto> getAllRequestsDto();

    boolean updatePaymentStatus(long id, PaymentStatus status);

    List<ApprovedRequestDto> getAllApprovedRequests();

    List<RequestDto> getRequestsByPage(int page, int limit);

    List<Request> getPagedProcessedRequestByUserId(long id, int offset, int limit);

    List<Request> getPagedUnprocessedRequestByUserId(long id, int offset, int limit);

    List<RequestDto> getRequestDtoList(List<Request> request);

    public List<ApprovedRequestDto> getApprovedRequestsByPage(int page, int limit);

    long getProcessedRequestByUserIdCount(long id);

    long getUnprocessedRequestByUserIdCount(long id);
}
