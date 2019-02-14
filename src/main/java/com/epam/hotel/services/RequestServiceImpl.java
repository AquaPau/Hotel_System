package com.epam.hotel.services;

import com.epam.hotel.daos.RequestDao;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.enums.Capacity;
import com.epam.hotel.model.enums.ClassID;
import com.epam.hotel.model.enums.PaymentStatus;
import com.epam.hotel.model.Request;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class RequestServiceImpl implements RequestService {
    private final RequestDao requestDao;

    RequestServiceImpl(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public Request create(Request entity) {
        requestValidation(entity);
        return requestDao.create(entity);
    }

    @Override
    public boolean delete(long id) {
        return requestDao.delete(id);
    }

    @Override
    public boolean update(Request entity) {
        requestValidation(entity);
        return requestDao.update(entity);
    }

    @Override
    public List<Request> getAll() {
        return requestDao.getAll();
    }

    @Override
    public Request getById(long id) {
        return requestDao.getById(id);
    }

    private void requestValidation(Request request) {
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

    @Override
    public List<Request> getUserRequests(long id) {
        return requestDao.getUserRequests(id);
    }

    @Override
    public List<Request> getPaymentStatus(PaymentStatus paymentStatus) {
        return requestDao.getPaymentStatus(paymentStatus);
    }

    public RequestDto getRequestDto(Request request) {
        requestValidation(request);
        return new RequestDto(request);
    }

    @Override
    public Request getRequestFromDto(RequestDto requestDto) {
        return requestDto.getRequest();
    }

    @Override
    public List<RequestDto> getUserRequestsDto(long id) {
        List<Request> userRequests = getUserRequests(id);
        return userRequests.stream().map(RequestDto::new).collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getAllRequestsDto() {
        List<Request> allRequests = getAll();
        return allRequests.stream().map(RequestDto::new).collect(Collectors.toList());
    }


}

