package com.epam.hotel.services.implementations;

import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import com.epam.hotel.repositories.ReservationRepository;
import com.epam.hotel.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(ReservationId id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (!optionalReservation.isPresent()) {
            throw new RuntimeException("Reservation not found");
        }
        return optionalReservation.get();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteById(ReservationId id) {
        reservationRepository.deleteById(id);
    }

}
