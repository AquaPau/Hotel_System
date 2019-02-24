package com.epam.hotel.repositories;

import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {

}
