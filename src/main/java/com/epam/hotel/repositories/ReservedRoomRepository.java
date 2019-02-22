package com.epam.hotel.repositories;

import com.epam.hotel.model.ReservedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedRoomRepository extends JpaRepository<ReservedRoom, Long> {
}