package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import com.epam.hotel.repositories.ReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    RequestServiceImpl requestService;

    private ReservationServiceImpl reservationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        reservationService = new ReservationServiceImpl(reservationRepository,requestService);
    }

    @Test
    void findById() {
        Reservation reservation = new Reservation();
        Optional<Reservation> optionalReservation = Optional.of(reservation) ;
        when(reservationRepository.findById(any(ReservationId.class))).thenReturn(optionalReservation);
        Reservation foundReservation = reservationService.findById(new ReservationId());
        assertEquals(reservation,foundReservation);
    }

    @Test
    void findByNotExistingId() {
        Optional<Reservation> optionalReservation = Optional.empty();
        when(reservationRepository.findById(any(ReservationId.class))).thenReturn(optionalReservation);
        Assertions.assertThrows(RuntimeException.class, () -> reservationService.findById(new ReservationId()));
    }

    }