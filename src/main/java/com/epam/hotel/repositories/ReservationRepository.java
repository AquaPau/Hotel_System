package com.epam.hotel.repositories;

import com.epam.hotel.domains.Reservation;
import com.epam.hotel.domains.ReservationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {

    // all approved reservations of a given period
    @Query("select r from Reservation r left join r.request s " +
            "where ((:checkin <= s.checkOut and :checkout >= s.checkIn) and (r.status='BILLSENT' or r.status='PAID'))")
    List<Reservation> findAllApprovedReservationOfThePeriod(@Param("checkin") Date check_in, @Param("checkout") Date check_out);

    @Query("select r from Reservation r left join r.request s " +
            "where ((:today >= s.checkIn and :today <= s.checkOut) and (r.status='PAID'))")
    List<Reservation> findAllReservationsForToday(@Param("today") Date today);


}
