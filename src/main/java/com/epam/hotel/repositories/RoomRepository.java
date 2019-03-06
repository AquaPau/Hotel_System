package com.epam.hotel.repositories;

import com.epam.hotel.domains.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select count (r) from Room r")
    long countAllRooms();

    @Query("select max (room.number) from Room room")
    int findLastNumber();

    Optional<Room> findByNumber(int number);
}