package com.epam.hotel.repositories;

import com.epam.hotel.domains.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}