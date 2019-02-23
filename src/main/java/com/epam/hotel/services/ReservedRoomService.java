package com.epam.hotel.services;

import com.epam.hotel.model.ReservedRoom;

import java.util.List;

public interface ReservedRoomService {

    List<ReservedRoom> findAll();

    ReservedRoom findById(Long id);

    ReservedRoom save(ReservedRoom reservedRoom);

    void deleteById(Long id);

}
