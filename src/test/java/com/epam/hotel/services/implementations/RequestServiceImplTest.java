package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.Request;
import com.epam.hotel.repositories.RequestRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RequestServiceImplTest {

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private DenyMessageServiceImpl denyMessageService;

    private RequestServiceImpl requestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        requestService = new RequestServiceImpl(requestRepository, denyMessageService);
    }

    @Test
    void findById() {
        Optional<Request> optionalRequest = Optional.of(new Request());
        when(requestRepository.findById(anyLong())).thenReturn(optionalRequest);
        Request foundRequest = requestService.findById(anyLong());
        assertEquals(optionalRequest.get(),foundRequest);
    }

    @Test
    void findByNotExistingId() {
        Optional<Request> optionalRequest = Optional.empty();
        when(requestRepository.findById(anyLong())).thenReturn(optionalRequest);
        Assertions.assertThrows(RuntimeException.class,() -> requestService.findById(anyLong()));
    }
}