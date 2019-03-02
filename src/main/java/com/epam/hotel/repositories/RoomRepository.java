package com.epam.hotel.repositories;

import com.epam.hotel.domains.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select count (r) from Room r")
    long countAllRooms();
}