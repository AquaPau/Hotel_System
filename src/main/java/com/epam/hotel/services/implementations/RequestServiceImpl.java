package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.DeniedRequest;
import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.User;
import com.epam.hotel.repositories.RequestRepository;
import com.epam.hotel.services.DenyMessageService;
import com.epam.hotel.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final DenyMessageService denyMessageService;

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request findById(Long id) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (!optionalRequest.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return optionalRequest.get();
    }

    @Override
    public Request save(Request request) {
        if (request.getDeniedRequest() == null) {
            DeniedRequest deniedRequest = new DeniedRequest();
            deniedRequest.setRequest(request);
            deniedRequest.setReason("");
            denyMessageService.save(deniedRequest);
        }
        return requestRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }


    @Override
    public Page<Request> getPagedUnprocessedRequestsByUser(User user, int page, int size) {
        return requestRepository.findUnprocessedRequestsByUser(user, PageRequest.of(page - 1, size, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Request> getPagedProcessedRequestsByUser(User user, int page, int size) {
        return requestRepository.getProcessedRequestsByUser(user, PageRequest.of(page - 1, size, Sort.Direction.DESC, "id"));
    }

    @Override
    public Page<Request> getPagedDeniedRequestsByUser(User user, int page, int size) {
        return requestRepository.findDeniedRequestsByUser(user, PageRequest.of(page - 1, size, Sort.Direction.DESC, "id"));
    }


    @Override
    public Page<Request> getAllPagedUnprocessedRequests(int page, int size) {
        return requestRepository.findAllUnprocessedRequests(PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<Request> getAllPagedProcessedRequests(int page, int size) {
        return requestRepository.findAllProcessedRequests(PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<Request> getAllPagedDeniedRequests(int page, int size) {
        return requestRepository.findAllDeniedRequests(PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public long countDeniedRequestByUser(User user) {
        return requestRepository.countDeniedRequestByUser(user);
    }

    @Override
    public long countProcessedRequestByUser(User user) {
        return requestRepository.countProcessedRequestByUser(user);
    }

    @Override
    public long countUnprocessedRequestByUser(User user) {
        return requestRepository.countUnprocessedRequestByUser(user);
    }

    @Override
    public long countAllPendingRequestForAdmin() {
        return requestRepository.countAllPendingRequestForAdmin();
    }

    @Override
    public long countAllApprovedRequestForAdmin() {
        return requestRepository.countAllApprovedRequestForAdmin();
    }

    @Override
    public long countAllDeniedRequestForAdmin() {
        return requestRepository.countAllDeniedRequestForAdmin();
    }


}
