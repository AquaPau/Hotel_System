package com.epam.hotel.services;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.User;
import org.springframework.data.domain.Page;

public interface RequestService {

    Request findById(Long id);

    Request save(Request request);

    Page<Request> getPagedUnprocessedRequestsByUser(User user, int page, int size);

    Page<Request> getPagedProcessedRequestsByUser(User user, int page, int size);

    Page<Request> getPagedDeniedRequestsByUser(User user, int page, int size);

    Page<Request> getAllPagedUnprocessedRequests(int page, int size);

    Page<Request> getAllPagedDeniedRequests(int page, int size);

    long countDeniedRequestByUser(User user);

    long countProcessedRequestByUser(User user);

    long countUnprocessedRequestByUser(User user);

    long countAllPendingRequestForAdmin();

    long countAllApprovedRequestForAdmin();

    long countAllDeniedRequestForAdmin();

}
