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


    Page<Request> getPagedUnprocessedRequestsByUser(User user, int page, int size);

    Page<Request> getPagedProcessedRequestsByUser(User user, int page, int size);

    Page<Request> getPagedDeniedRequestsByUser(User user, int page, int size);



    Page<Request> getAllPagedUnprocessedRequests(int page, int size);

    Page<Request> getAllPagedProcessedRequests(int page, int size);

    Page<Request> getAllPagedDeniedRequests(int page, int size);

}
