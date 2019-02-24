package com.epam.hotel.services.implementations;

import com.epam.hotel.model.Request;
import com.epam.hotel.model.User;
import com.epam.hotel.repositories.RequestRepository;
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
        return requestRepository.save(request);
    }

    @Override
    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public Page<Request> getPagedUnprocessedRequestByUser(User user, int page, int size) {
        return requestRepository.findAllByUserAndRoomNull(user, PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }

    @Override
    public Page<Request> getPagedProcessedRequestByUser(User user, int page, int size) {
        return requestRepository.findAllByUserAndRoomNotNull(user, PageRequest.of(page - 1, size, Sort.Direction.ASC, "id"));
    }


}
