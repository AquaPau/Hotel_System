package com.epam.hotel.services;

import com.epam.hotel.daos.RequestDao;
import com.epam.hotel.enums.Capacity;
import com.epam.hotel.enums.ClassID;
import com.epam.hotel.enums.PaymentStatus;
import com.epam.hotel.model.Request;

import java.util.List;

public class RequestServiceImpl implements RequestService {
    private final RequestDao requestDao;

    RequestServiceImpl(RequestDao requestDao){
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
        if (request.getUserID() <= 0 || request.getCheckIn() == null || request.getCheckOut() == null) {
            throw new IllegalArgumentException("Request is not valid: fill the required fields");
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
}

