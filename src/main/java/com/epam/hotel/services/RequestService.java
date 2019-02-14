package com.epam.hotel.services;

import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.model.Request;

import java.util.List;

public interface RequestService extends Service<Request> {
    List<Request> getUserRequests(long id);

    List<Request> getPaymentStatus(PaymentStatus paymentStatus);

    RequestDto getRequestDto(Request request);

    Request getRequestFromDto(RequestDto requestDto);

    List<RequestDto> getUserRequestsDto(long id);

    List<RequestDto> getAllRequestsDto();
}
