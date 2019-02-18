package com.epam.hotel.services;

import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.User;

import java.util.List;

public interface ReservedRoomService extends Service<ReservedRoom> {

    List<ReservedRoom> getReservationsOfUser(User user);

    List<ReservedRoom> getReservationsByUserId(long userId);
}
