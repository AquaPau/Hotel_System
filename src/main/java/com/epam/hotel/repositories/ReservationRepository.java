package com.epam.hotel.repositories;

import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {

    @Query("SELECT res FROM Reservation res JOIN Request r WHERE (:#{#checkin} BETWEEN r.check_in AND r.check_out OR " +
            ":#{#checkout} BETWEEN r.check_in AND r.check_out)")
    List<Reservation> findAllReservationOfThePeriod (@Param("checkin") Date check_in, @Param("checkout") Date check_out);

    /*@Query("SELECT r from Reservation r join r.request ")
    List<Reservation> findAllReservationOf11ThePeriod (@Param("check_in")Date check_in, @Param("check_out") Date check_out);*/
}
