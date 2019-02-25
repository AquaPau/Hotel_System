package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.DenyMessage;
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
        if (request.getDenyMessage() == null) {
            DenyMessage denyMessage = new DenyMessage();
            denyMessage.setRequest(request);
            denyMessage.setMessage("");
            denyMessageService.save(denyMessage);
        }
        return requestRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public Page<Request> getPagedUnprocessedRequestByUser(User user, int page, int size) {
        return requestRepository.findAllUnprocessedRequestsByUser(user, PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<Request> getPagedProcessedRequestByUser(User user, int page, int size) {
        return requestRepository.findAllProcessedRequestsByUser(user, PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<Request> getPagedUnprocessedRequest(int page, int size) {
        return requestRepository.findAllUnprocessedRequests(PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }


}
