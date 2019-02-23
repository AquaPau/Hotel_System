package com.epam.hotel.services;

import com.epam.hotel.model.Request;

import java.util.List;

public interface RequestService {

    List<Request> findAll();

    Request findById(Long id);

    Request save(Request request);

    void deleteById(Long id);


}
