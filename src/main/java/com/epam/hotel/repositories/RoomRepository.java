package com.epam.hotel.repositories;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RoomRepository extends JpaRepository<Room, Long> {

    /*@Query(value = "SELECT room.id " +
            "FROM hotel.room AS room INNER JOIN hotel.reservation AS res ON room.id = res.room_id" +//FULL OUTER??
            "WHERE r.id NOT IN " +
            "(SELECT res.room_id FROM hotel.reservaion AS res JOIN hotel.request AS req " +
            "ON res.request_id = req.id " +
            "WHERE " +
            "req.check_in <= :#{#request.checkIn} AND req.checkout >= :#{#request.checkIn} " +
            "OR " +
            "req.checkin <= :#{#request.checkOut} AND req.checkout >= :#{#request.checkOut}", nativeQuery = true)
    Page <Room> findAllRoomsByRequest(Request request, int page, int limit);*/
}