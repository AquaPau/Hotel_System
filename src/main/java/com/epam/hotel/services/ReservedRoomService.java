package com.epam.hotel.services;

import com.epam.hotel.dtos.ProcessedRequestDto;
import com.epam.hotel.dtos.RequestDto;
import com.epam.hotel.model.Request;
import com.epam.hotel.model.ReservedRoom;
import com.epam.hotel.model.User;

import java.util.List;

public interface ReservedRoomService extends Service<ReservedRoom> {

    List<ReservedRoom> getReservationsOfUser(User user);

    List<ReservedRoom> getReservationsByUserId(long userId);

    ProcessedRequestDto getProcessedRequestDto(ReservedRoom reservedRoom);

    List<RequestDto> getAllUnprocessedRequestDtoOfUser(User user, List<RequestDto> userRequestDtoList);

    List<ProcessedRequestDto> getAllProcessedRequestDtoOfUser(User user);

    ProcessedRequestDto getProcessedRequestDto(Request request);

    List<ProcessedRequestDto> getProcessedRequestDtoList(List<Request> requestList);

    void cancelReservation(long id);
}
