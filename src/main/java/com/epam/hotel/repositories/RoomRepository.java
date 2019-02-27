package com.epam.hotel.repositories;

import com.epam.hotel.domains.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findAllByIdIsNotIn(List<Room> notAvailableRooms, Pageable pageable);

}