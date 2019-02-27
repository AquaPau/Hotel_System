package com.epam.hotel.services;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;

import java.util.List;

public interface ReservationService {

    List<Reservation> findAll();

    Reservation findById(ReservationId id);

    Reservation save(Reservation reservation);

    void deleteById(ReservationId id);

    List <Reservation> findAllReservationOfThePeriod(Request request);

}
