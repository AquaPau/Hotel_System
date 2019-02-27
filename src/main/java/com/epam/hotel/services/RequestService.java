package com.epam.hotel.services;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RequestService {

    List<Request> findAll();

    Request findById(Long id);

    Request save(Request request);

    void deleteById(Long id);


    Page<Request> getPagedUnprocessedRequestByUser(User user, int page, int size);

    Page<Request> getPagedProcessedRequestByUser(User user, int page, int size);

    Page<Request> getPagedDeniedRequestsByUser(User user, int page, int size);



    Page<Request> getAllPagedUnprocessedRequest(int page, int size);

    Page<Request> getAllPagedProcessedRequest(int page, int size);

    Page<Request> getAllPagedDeniedRequests(int page, int size);

}
