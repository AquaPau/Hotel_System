package com.epam.hotel.repositories;

import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {

    @Query("select r from Reservation r where status <> 'DENIED' order by r.id")
    Page<Reservation> findAllApprovedReservations(Pageable pageable);

    @Query("select r from Reservation r where status = 'DENIED' order by r.id")
    Page<Reservation> findAllDeniedReservations(Pageable pageable);

}
