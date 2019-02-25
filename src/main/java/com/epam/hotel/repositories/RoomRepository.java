package com.epam.hotel.repositories;

import com.epam.hotel.domains.Request;
import com.epam.hotel.domains.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT rooms.roomid AS roomid, rooms.roomnumber AS roomnumber, rooms.capacity AS capacity, " +
            "rooms.classid AS classid, rooms.price AS price " +
            "FROM hotel.rooms AS rooms JOIN hotel.reservedrooms as res ON rooms.roomid = res.roomid " +//FULL OUTER??

            "WHERE rooms.roomid NOT IN " +
            "(SELECT res.roomid FROM hotel.reservedrooms AS res JOIN hotel.requests AS req " +
            "ON res.requestid = req.requestid " +
            "WHERE " +
            "req.checkin <= :checkin AND req.checkout >= :checkin " +
            "OR " +
            "req.checkin <= :checkout AND req.checkout >= :checkout " +
            ") " +
            "AND rooms.capacity IN (:capacityList) " +
            "ORDER BY " +
            "CASE " +
            "WHEN classid = :class1 THEN 1 " +
            "WHEN classid = :class2 THEN 2 " +
            "WHEN classid = :class3 THEN 3 " +
            "WHEN classid = :class4 THEN 4 " +
            "END " +
            "CASE " +
            "WHEN capacity = :cap1 THEN 1 " +
            "WHEN capacity = :cap2 THEN 2 " +
            "WHEN capacity = :cap3 THEN 3 " +
            "WHEN capacity = :cap4 THEN 4 " +
            "END")
    Page<Room> findAllAvailableForRequest(Request request, int page, int limit);
}