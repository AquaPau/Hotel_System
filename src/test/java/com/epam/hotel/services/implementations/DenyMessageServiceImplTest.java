package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.DeniedRequest;
import com.epam.hotel.repositories.DenyMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DenyMessageServiceImplTest {

    @Mock
    DenyMessageRepository denyMessageRepository;

    DenyMessageServiceImpl denyMessageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        denyMessageService = new DenyMessageServiceImpl(denyMessageRepository);
    }

    @Test
    void findById() {
        Optional<DeniedRequest> testRequest = Optional.of(new DeniedRequest());
        when(denyMessageRepository.findById(anyLong())).thenReturn(testRequest);
        DeniedRequest foundRequest = denyMessageService.findById(1l);
        assertEquals(foundRequest, testRequest.get());
    }

}