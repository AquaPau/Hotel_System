package com.epam.hotel.services;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReservationService {

    Reservation findById(ReservationId id);

    Reservation save(Reservation reservation);

    Page<Reservation> getAllApprovedReservationsPaged(int page, int size);

    List<Reservation> findAllApprovedReservationOfThePeriodByRequest(Request request);

    Page<Reservation> findAllReservationsForToday(int page, int size);

}
